package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-11-26.
 */
import com.example.teuljung.practice2.*;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;

public class HeaderLayout extends LinearLayout{
    private TextView mTextView;
    private TabLayout mTabLayout;
    public  HeaderLayout(Context context) {
        super(context);
    }
    public HeaderLayout(Context context,AttributeSet attrs) {
        super(context, attrs);
    }
    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.text_view);
    }
    public int getScrollRange() {
        return getHeight()-mTextView.getHeight();
    }
}
