package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-11-26.
 */
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MyRecyclerView extends RecyclerView{
    public MyRecyclerView(Context context) {
        super(context);
    }
    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
    }
    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
    }
}
