package com.tr4android.recyclerviewslideitem;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
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

    private boolean mUndoable;

    private boolean mUndone;

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

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mSwipeItem.getMeasuredHeight());

        mSwipeInfo.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        mSwipeItem.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mFirstLayout) {
            final int parentLeft = getPaddingLeft();
            final int parentRight = right - left - getPaddingRight();

            final int parentTop = getPaddingTop();

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
        ((TextView) mSwipeInfo.findViewById(R.id.undoDescription)).setTextColor(resolvedTextColor);
        ((TextView) mSwipeInfo.findViewById(R.id.undoButton)).setTextColor(resolvedTextColor);
    }

    public void setSwipeUndoable(boolean undoable) {
        mUndoable = undoable;
    }

    public void setSwipeConfiguration(SwipeConfiguration configuration) {
        mSwipeInfo = getChildAt(0);
        setSwipeImageResource(configuration.getDrawableResId());
        setSwipeDescription(configuration.getDescription());
        setSwipeDescriptionTextColor(configuration.getDescriptionTextColor());
        setSwipeBackgroundColor(configuration.getBackgroundColor());
        setSwipeUndoable(configuration.getUndoable());
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
        public int getViewHorizontalDragRange(View child) {
            return mHorizontalDragRange;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                if (mSwipeItem.getLeft() == -mHorizontalDragRange) {
                    handleLeftSwipe();
                } else if (mSwipeItem.getLeft() == mHorizontalDragRange) {
                    handleRightSwipe();
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

    private void handleLeftSwipe() {
        if (mUndoable) {
            final Handler handler = new Handler();
            final Runnable removeItemRunnable = new Runnable() {
                @Override
                public void run() {
                    dispatchOnSwipeLeft();
                }
            };
            handler.postDelayed(removeItemRunnable, 5000); // 5 sec to undo
            showUndoAction(true);
            mSwipeInfo.findViewById(R.id.undoButton).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.removeCallbacks(removeItemRunnable);
                    showUndoAction(false);
                    mSwipeInfo.setOnClickListener(null);
                    swipeBack();
                }
            });
        } else {
            dispatchOnSwipeLeft();
        }
    }

    private void handleRightSwipe() {
        if (mUndoable) {
            final Handler handler = new Handler();
            final Runnable removeItemRunnable = new Runnable() {
                @Override
                public void run() {
                    dispatchOnSwipeRight();
                }
            };
            handler.postDelayed(removeItemRunnable, 5000); // 5 sec to undo
            showUndoAction(true);
            mSwipeInfo.findViewById(R.id.undoButton).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.removeCallbacks(removeItemRunnable);
                    showUndoAction(false);
                    mSwipeInfo.setOnClickListener(null);
                    swipeBack();
                }
            });
        } else {
            dispatchOnSwipeRight();
        }
    }

    private void swipeBack() {
        if (mDragHelper.smoothSlideViewTo(mSwipeItem, 0, mSwipeItem.getTop())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void showUndoAction(final boolean show) {
        View undoItem = mSwipeInfo.findViewById(R.id.undoLayout);
        View infoItem = mSwipeInfo.findViewById(R.id.infoLayout);
        ViewCompat.setAlpha(undoItem, show ? 0 : 1);
        ViewCompat.setAlpha(infoItem, show ? 1 : 0);
        if(show) undoItem.setVisibility(VISIBLE);
        if(!show) infoItem.setVisibility(VISIBLE);
        final ViewPropertyAnimatorCompat undoAnimation = ViewCompat.animate(undoItem);
        undoAnimation.setDuration(300)
                .alpha(show ? 1 : 0).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
            }

            @Override
            public void onAnimationEnd(View view) {
                undoAnimation.setListener(null);
                ViewCompat.setAlpha(view, show ? 1 : 0);
                if(!show) view.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationCancel(View view) {
            }
        }).start();
        final ViewPropertyAnimatorCompat infoAnimation = ViewCompat.animate(infoItem);
        infoAnimation.setDuration(300)
                .alpha(show ? 0 : 1).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
            }

            @Override
            public void onAnimationEnd(View view) {
                infoAnimation.setListener(null);
                ViewCompat.setAlpha(view, show ? 0 : 1);
                if (show) view.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationCancel(View view) {
            }
        }).start();
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
