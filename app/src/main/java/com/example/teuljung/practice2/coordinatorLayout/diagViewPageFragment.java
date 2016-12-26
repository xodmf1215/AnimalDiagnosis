package com.example.teuljung.practice2.coordinatorLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teuljung.practice2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teul jung on 2016-12-13.
 */
public class diagViewPageFragment extends Fragment{
    public static diagViewPageFragment createInstance() {
        final diagViewPageFragment pageFragment = new diagViewPageFragment();
        final Bundle bundle = new Bundle();// 번들이 뭔가? argument 라고 보면 된다. putInt putString 같은 메소드로 키와 value를 연결할 수 있다.
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diag_page_fragment, container, false);
    }
}