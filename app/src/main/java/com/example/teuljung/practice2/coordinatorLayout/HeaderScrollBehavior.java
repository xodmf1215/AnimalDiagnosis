package com.example.teuljung.practice2.coordinatorLayout;

/**
 * Created by teul jung on 2016-11-26.
 */
import com.example.teuljung.practice2.*;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

public class HeaderScrollBehavior extends ViewOffsetBehavior<HeaderLayout> {
    private static final String TAG = HeaderScrollBehavior.class.getSimpleName();

    private int mTouchSlop;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;

    private boolean mIsScrolling;

    private View mTabLayout;

    private int mMinOffset;
    private int mMaxOffset;

    private  View mTargetView;
    private int mSkippedOffset;

    private ViewFlinger mViewFlinger;

    public HeaderScrollBehavior() {

    }
    public HeaderScrollBehavior(Context context,AttributeSet attrs) {
        super(context, attrs);
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    }

    public boolean onLayoutChild(CoordinatorLayout parent, HeaderLayout child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    public boolean onMeasureChild(CoordinatorLayout parent, HeaderLayout child, int parentWidthMeasureSpec,
                                  int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec,
                heightUsed);
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, HeaderLayout child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, HeaderLayout child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, HeaderLayout child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        if (mViewFlinger != null) {
            mViewFlinger.cancel();
        }

        if ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0) {
            mTargetView = target;

            mIsScrolling = false;
            mSkippedOffset = 0;

            mTabLayout = child.findViewById(R.id.tab_layout);

            mMinOffset = -(child.getHeight() - mTabLayout.getHeight());
            mMaxOffset = 0;

            return true;
        }
        return false;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, HeaderLayout child, View target, int dx, int dy,
                                  int[] consumed) {
        if (!mIsScrolling) {
            mSkippedOffset += dy;

            if (Math.abs(mSkippedOffset) >= mTouchSlop) {
                mIsScrolling = true;
                target.getParent().requestDisallowInterceptTouchEvent(true);
            }
        }

        if (mIsScrolling && dy != 0) {
            int min = -child.getScrollRange();
            int max = 0;

            int currentOffset = getTopAndBottomOffset();
            int newOffset = Math.min(Math.max(min, currentOffset - dy), max);

            consumed[1] = newOffset - currentOffset;

            setTopAndBottomOffset(newOffset);
        }
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, HeaderLayout child, View target,
                                    float velocityX, float velocityY) {
        if (mViewFlinger != null) {
            mViewFlinger.cancel();
        } else {
            mViewFlinger = new ViewFlinger(coordinatorLayout);
        }

        final int targetOffsetRange;
        final int targetOffset;
        if (target instanceof ScrollingView) {
            targetOffsetRange = ((ScrollingView) target).computeVerticalScrollRange() + target.getPaddingTop()
                    + target.getPaddingBottom();
            targetOffset = ((ScrollingView) target).computeVerticalScrollOffset();
        } else {
            targetOffsetRange = Math.max(0, target.getHeight() - coordinatorLayout.getHeight());
            targetOffset = target.getScrollY();
        }

        mViewFlinger.fling((int) velocityY, targetOffset, targetOffsetRange);

        return true;
    }

    private class ViewFlinger implements Runnable {
        private final ScrollerCompat mScroller;
        private final CoordinatorLayout mCoordinatorLayout;
        private int mLastFlingY;

        public ViewFlinger(CoordinatorLayout coordinatorLayout) {
            mScroller = ScrollerCompat.create(coordinatorLayout.getContext());
            mCoordinatorLayout = coordinatorLayout;
        }

        public void fling(int velocity, int targetOffset, int targetOffsetRange) {
            if (Math.abs(velocity) < mMinFlingVelocity) {
                return;
            }

            velocity = Math.max(-mMaxFlingVelocity, Math.min(velocity, mMaxFlingVelocity));

            mScroller.fling(0, 0, 0, velocity, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            mCoordinatorLayout.postOnAnimation(this);

            mLastFlingY = 0;
        }

        public void cancel() {
            mScroller.abortAnimation();
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                int dy = mScroller.getCurrY() - mLastFlingY;

                final int selfOffset = getTopAndBottomOffset();
                final int newSelfOffset = Math.max(mMinOffset, Math.min(mMaxOffset, selfOffset - dy));
                final int skipped = newSelfOffset - selfOffset + dy;

                final boolean selfFinished = !setTopAndBottomOffset(newSelfOffset);

                final int targetOffset;
                final boolean targetFinished;
                if (mTargetView instanceof ScrollingView) {
                    targetOffset = ((ScrollingView) mTargetView).computeVerticalScrollOffset();
                    mTargetView.scrollBy(0, skipped);
                    targetFinished = (targetOffset == ((ScrollingView) mTargetView).computeVerticalScrollOffset());
                } else {
                    targetOffset = mTargetView.getScrollY();
                    mTargetView.scrollBy(0, skipped);
                    targetFinished = (targetOffset == mTargetView.getScrollY());
                }

                final boolean scrollerFinished = mScroller.isFinished();

                if (scrollerFinished || (selfFinished && targetFinished)) {
                    return;
                }

                mCoordinatorLayout.postOnAnimation(this);

                mLastFlingY = mScroller.getCurrY();
            }
        }
    }
}
