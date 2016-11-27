package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-11-26.
 */

import android.content.Context;
import android.support.design.widget.*;
import android.util.AttributeSet;
import android.view.View;

public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior {
    private ViewOffsetHelper mViewOffsetHelper;
    private int mTempTopBottomOffset=0;
    public ViewOffsetBehavior() {
    }
    public ViewOffsetBehavior(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public boolean onLayoutChild(CoordinatorLayout parent,View child,int layoutDirection) { //에러 나서 V에서 View로 바꿨음 generic에서 erasure관련 에러
        parent.onLayoutChild(child, layoutDirection);
        if(mViewOffsetHelper  == null) {
            mViewOffsetHelper = new ViewOffsetHelper(child); //OffsetHelper에 View를 넘거줌
        }
        mViewOffsetHelper.onViewLayout();
        if(mTempTopBottomOffset != 0) {
            mViewOffsetHelper.setTopAndBottomOffset(mTempTopBottomOffset);
            mTempTopBottomOffset = 0;
        }
        return true;
    }

    public boolean setTopAndBottomOffset(int offset) {
        if(mViewOffsetHelper != null) {
            return mViewOffsetHelper.setTopAndBottomOffset(offset);
        }
        else {
            mTempTopBottomOffset = offset;
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return mViewOffsetHelper != null ? mViewOffsetHelper.getTopAndBottomOffset() : 0;
    }
}