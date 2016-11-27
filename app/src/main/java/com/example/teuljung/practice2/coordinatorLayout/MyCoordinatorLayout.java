package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-11-26.
 */
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

public class MyCoordinatorLayout extends  CoordinatorLayout{
    public MyCoordinatorLayout(Context context) {
        super(context);
    }
    public MyCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public MyCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }
}
