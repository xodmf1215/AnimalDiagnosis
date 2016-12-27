package com.example.teuljung.practice2.coordinatorLayout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.teuljung.practice2.R;
import com.example.teuljung.practice2.imageMap.ColorTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teul jung on 2016-12-13.
 */
public class diagViewPageFragment extends Fragment {
    public static diagViewPageFragment createInstance() {
        final diagViewPageFragment pageFragment = new diagViewPageFragment();
        final Bundle bundle = new Bundle();// 번들이 뭔가? argument 라고 보면 된다. putInt putString 같은 메소드로 키와 value를 연결할 수 있다.
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diag_page_fragment,container,false);
        ImageView iv = (ImageView) v.findViewById (R.id.image);

        if (iv != null) {
            iv.setOnTouchListener (new View.OnTouchListener() {
                public boolean onTouch(View v,MotionEvent ev) {
                    boolean handledHere = false;
                    boolean uphandle=false;
                    int selectedColor=-1;

                    final int action = ev.getAction();

                    final int evX = (int) ev.getX();
                    final int evY = (int) ev.getY();
                    int nextImage = -1;			// resource id of the next image to display

                    // If we cannot find the imageView, return.
                    ImageView imageView = (ImageView) v.findViewById (R.id.image);
                    if (imageView == null) return false;

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            handledHere = true;
                            Fragment fr = getChildFragmentManager().findFragmentByTag("scFragment");
                            if(fr != null) {
                                getChildFragmentManager().beginTransaction().remove(fr).commit();
                            }
                            //getChildFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("scFragment")).commit();
                            break;
                        case MotionEvent.ACTION_UP :
                            uphandle=true;
                            int touchColor = getHotspotColor (R.id.image_areas, evX, evY);
                            int redV=Color.red(touchColor);
                            int blueV=Color.blue(touchColor);
                            int greenV=Color.green(touchColor);

                            // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
                            // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
                            // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
                            // varying pixel density.
                            int tolerance = Math.abs(Color.RED-touchColor);
                            selectedColor = 1;
                            if (Math.abs(Color.BLUE-touchColor)<tolerance) {
                                selectedColor=2;
                                tolerance=Math.abs(Color.BLUE-touchColor);
                            }
                            if (Math.abs(Color.GREEN-touchColor)<tolerance) {
                                selectedColor=3;
                                tolerance=Math.abs(Color.GREEN-touchColor);
                            }
                            if (Math.abs(Color.YELLOW-touchColor)<tolerance) {
                                selectedColor=4;
                                tolerance=Math.abs(Color.YELLOW-touchColor);
                            }
                            if (Math.abs(Color.WHITE-touchColor)<tolerance) {
                                selectedColor=5;
                                tolerance=Math.abs(Color.WHITE-touchColor);
                            }

                            // If the next image is the same as the last image, go back to the default.
                            // toast ("Current image: " + currentResource + " next: " + nextImage);
                            //if (currentResource == nextImage) {
                            //    nextImage = R.drawable.p2_ship_default;
                            //}
                            handledHere = true;
                            break;

                        default:
                            handledHere = false;
                    } // end switch

                    if (handledHere && uphandle) {
                        if(selectedColor==5) return handledHere;
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        Fragment fragTwo = new checkListFragment();
                        Bundle arguments = new Bundle();
                        arguments.putInt("selectedColor",selectedColor);
                        fragTwo.setArguments(arguments);
                        ft.add(R.id.fragment, fragTwo,"scFragment");
                        ft.commit();
                    }
                    return handledHere;
                }
            });
        }
        return v;
    }
    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) getView().findViewById(hotspotId);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }
}