package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-11-26.
 */
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
//이 클래스를 생성하면 뷰를 받아오고 updateOffsets 메소드를 통해 offset을 설정한다.
public class ViewOffsetHelper {
    private final View mView;
    private int mLayoutTop;
    private int mOffsetTop;

    public ViewOffsetHelper(View view) {
        mView=view;
    }
    public void onViewLayout() {
        mLayoutTop=mView.getTop();
        updateOffsets();
    }
    private void updateOffsets() {
        if(Build.VERSION.SDK_INT == 22) {
            ViewCompat.setTranslationY(mView,(float)mOffsetTop);
        }
        else {
            ViewCompat.offsetTopAndBottom(mView,mOffsetTop-mView.getTop()-mLayoutTop);
        }
    }
    public boolean setTopAndBottomOffset(int offset) {
        if(mOffsetTop != offset) {
            mOffsetTop = offset;
            updateOffsets();
            return true;
        }
        else {
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return mOffsetTop;
    }
}
