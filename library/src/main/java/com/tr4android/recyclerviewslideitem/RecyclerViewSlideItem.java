package com.tr4android.recyclerviewslideitem;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ThomasR on 28.04.2015.
 */
public class RecyclerViewSlideItem extends ViewGroup {
    private static final String LOG_TAG = "SlideItem";

    private final ViewDragHelper mDragHelper;

    private int mHorizontalDragRange;

    private View mSlideItem;

    private View mSlideInfo;

    private int mSlideBackgroundColor;

    private Drawable mSlideDrawable;

    private String mSlideDescription;

    private int mSlideDescriptionColor;

    private boolean mFirstLayout = true;

    public RecyclerViewSlideItem(Context context) {
        this(context, null);
    }

    public RecyclerViewSlideItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewSlideItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // retrieve attributes from xml
        if (attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewSlideItem);
            if (ta != null){
                mSlideBackgroundColor = ta.getColor(R.styleable.RecyclerViewSlideItem_slideBackgroundColor, Color.TRANSPARENT);
                mSlideDrawable = ta.getDrawable(R.styleable.RecyclerViewSlideItem_slideDrawable);
                mSlideDescription = ta.getString(R.styleable.RecyclerViewSlideItem_slideDescription);
                mSlideDescriptionColor = ta.getColor(R.styleable.RecyclerViewSlideItem_slideDescriptionColor, Color.BLACK);
            }
            ta.recycle();
        }

        // inflate slide view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideInfo = inflater.inflate(R.layout.item_slide_info, null);

        // set attributes to slide view
        slideInfo.setBackgroundColor(mSlideBackgroundColor);
        ImageView imageViewLeft = (ImageView) slideInfo.findViewById(R.id.imageViewLeft);
        imageViewLeft.setImageDrawable(mSlideDrawable);
        ImageView imageViewRight = (ImageView) slideInfo.findViewById(R.id.imageViewRight);
        imageViewRight.setImageDrawable(mSlideDrawable);
        TextView textViewDescription = (TextView) slideInfo.findViewById(R.id.textViewDescription);
        textViewDescription.setText(mSlideDescription);
        textViewDescription.setTextColor(mSlideDescriptionColor);

        // add slide view
        addViewInLayout(slideInfo, 0, generateDefaultLayoutParams(), true);

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
        mSlideInfo = getChildAt(0);
        mSlideItem = getChildAt(1);
        measureChildWithMargins(mSlideInfo, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(mSlideItem, widthMeasureSpec, 0, heightMeasureSpec, 0);
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, mSlideItem.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(mFirstLayout) {
            final int parentLeft = getPaddingLeft();
            final int parentRight = right - left - getPaddingRight();

            final int parentTop = getPaddingTop();
            final int parentBottom = bottom - top - getPaddingBottom();

            mHorizontalDragRange = getMeasuredWidth();

            mSlideInfo.layout(parentLeft, parentTop, parentRight, parentTop + mSlideItem.getMeasuredHeight());
            mSlideItem.layout(parentLeft, parentTop, parentRight, parentTop + mSlideItem.getMeasuredHeight());
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
            Log.i(LOG_TAG, "View scrolled");
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == mSlideItem;
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
        return new RecyclerViewSlideItem.LayoutParams(getContext(), attrs);
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
