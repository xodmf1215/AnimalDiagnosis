package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-12-12.
 */
import com.example.teuljung.practice2.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridViewPageFragment_dog extends Fragment {
    private static final int ITEM_COUNT = 20;

    private RecyclerView mGridView;
    private List<String> mItemList;

    public static GridViewPageFragment_dog createInstance() {
        final GridViewPageFragment_dog pageFragment = new GridViewPageFragment_dog();
        final Bundle bundle = new Bundle();// 번들이 뭔가?
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGridView = (RecyclerView) inflater.inflate(R.layout.gridview_page_fragment, container, false);
        setup();
        return mGridView;
    }

    private void setup() {
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            itemList.add("Item " + i);
        }
        mItemList = itemList;

        final GridRecyclerViewAdapter adapter = new GridRecyclerViewAdapter();
        mGridView.setAdapter(adapter);

        mGridView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerItemViewHolder> {

        public GridRecyclerViewAdapter() {
        }

        @Override
        public GridRecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //task 2
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            final int layoutRes=R.layout.grid_item_3;

            final View view = inflater.inflate(layoutRes, parent, false);

            return new GridRecyclerItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GridRecyclerItemViewHolder viewHolder, int position) { //task 3
            final TextView titleView = (TextView) viewHolder.itemView.findViewById(R.id.item_title);
            if (titleView != null) {
                titleView.setText(String.valueOf(position));
            }
        }

        @Override
        public int getItemCount() {
            return mItemList == null ? 0 : mItemList.size();
        }//task 1

        @Override
        public int getItemViewType(int position) {
            return 1;
        }
    }

    public static class GridRecyclerItemViewHolder extends RecyclerView.ViewHolder {
        public GridRecyclerItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}