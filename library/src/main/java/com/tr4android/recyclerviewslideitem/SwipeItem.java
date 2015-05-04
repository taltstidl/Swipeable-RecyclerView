package com.tr4android.recyclerviewslideitem;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThomasR on 28.04.2015.
 */
public class SwipeItem extends ViewGroup {
    private static final String LOG_TAG = "SwipeItem";

    private final ViewDragHelper mDragHelper;

    private SwipeListener mSwipeListener;

    private int mHorizontalDragRange;

    private View mSwipeItem;

    private View mSwipeInfo;

    private boolean mFirstLayout = true;

    public SwipeItem(Context context) {
        this(context, null);
    }

    public SwipeItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFirstLayout = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFirstLayout = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h != oldh) {
            mFirstLayout = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // measure child
        mSwipeInfo = getChildAt(0);
        mSwipeItem = getChildAt(1);
        measureChildWithMargins(mSwipeInfo, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(mSwipeItem, widthMeasureSpec, 0, heightMeasureSpec, 0);
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, mSwipeItem.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(mFirstLayout) {
            final int parentLeft = getPaddingLeft();
            final int parentRight = right - left - getPaddingRight();

            final int parentTop = getPaddingTop();
            final int parentBottom = bottom - top - getPaddingBottom();

            mHorizontalDragRange = getMeasuredWidth();

            mSwipeInfo.layout(parentLeft, parentTop, parentRight, parentTop + mSwipeItem.getMeasuredHeight());
            mSwipeItem.layout(parentLeft, parentTop, parentRight, parentTop + mSwipeItem.getMeasuredHeight());
            mFirstLayout = false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setSwipeBackgroundColor(int resolvedColor) {
        mSwipeInfo.setBackgroundColor(resolvedColor);
    }

    public void setSwipeImageResource(int resId) {
        ((ImageView) mSwipeInfo.findViewById(R.id.imageViewLeft)).setImageResource(resId);
        ((ImageView) mSwipeInfo.findViewById(R.id.imageViewRight)).setImageResource(resId);
    }

    public void setSwipeDescription(CharSequence description) {
        ((TextView) mSwipeInfo.findViewById(R.id.textViewDescription)).setText(description);
    }

    public void setSwipeDescriptionTextColor(int resolvedTextColor) {
        ((TextView) mSwipeInfo.findViewById(R.id.textViewDescription)).setTextColor(resolvedTextColor);
    }

    public void setSwipeConfiguration(SwipeConfiguration configuration) {
        mSwipeInfo = getChildAt(0);
        setSwipeImageResource(configuration.getDrawableResId());
        setSwipeDescription(configuration.getDescription());
        setSwipeDescriptionTextColor(configuration.getDescriptionTextColor());
        setSwipeBackgroundColor(configuration.getBackgroundColor());
    }

    public void setSwipeListener(SwipeListener listener) {
        mSwipeListener = listener;
    }

    public interface SwipeListener {
        /**
         * Called when the SwipeItem was swiped away to the left
         */
        public void onSwipeLeft();
        /**
         * Called when the SwipeItem was swiped away to the right
         */
        public void onSwipeRight();
    }

    void dispatchOnSwipeLeft() {
        if (mSwipeListener != null) {
            mSwipeListener.onSwipeLeft();
        }
    }

    void dispatchOnSwipeRight() {
        if (mSwipeListener != null) {
            mSwipeListener.onSwipeRight();
        }
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == mSwipeItem;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mHorizontalDragRange;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                if (mSwipeItem.getLeft() == -mHorizontalDragRange) {
                    dispatchOnSwipeLeft();
                } else if (mSwipeItem.getLeft() == mHorizontalDragRange) {
                    dispatchOnSwipeRight();
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // logic for slide behaviour
            if (xvel < 0) {
                // dismiss to left
                mDragHelper.settleCapturedViewAt(-mHorizontalDragRange, releasedChild.getTop());
            } else if (xvel > 0) {
                // dismiss to right
                mDragHelper.settleCapturedViewAt(mHorizontalDragRange, releasedChild.getTop());
            } else {
                // not enough velocity to dismiss
                mDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
            }

            invalidate();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new SwipeItem.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
