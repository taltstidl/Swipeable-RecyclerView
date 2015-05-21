/*
 * Copyright (C) 2015 Thomas Robert Altstidl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tr4android.recyclerviewslideitem;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

public class SwipeItem extends ViewGroup {
    //private static final String LOG_TAG = "SwipeItem";

    private final ViewDragHelper mDragHelper;

    private OnSwipeListener mOnSwipeListener;

    private int mHorizontalDragRange;

    // custom view that slides
    private View mSwipeItem;

    // included background view for slide hint
    private View mSwipeInfo;

    // included background view for slide undo
    private View mSwipeUndo;

    private SwipeConfiguration mConfiguration;

    private boolean mFirstLayout = true;

    private int mTouchSlop;

    private int mPreviousPosition = 0;

    private boolean mHasPassedLeftThreshold;

    private boolean mHasPassedRightThreshold;

    protected enum SwipeState {
        LEFT_UNDO, RIGHT_UNDO, NORMAL
    }

    private SwipeState mState;

    // click listeners for undo actions
    private final OnClickListener leftUndoClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showUndoAction(false);
            mSwipeInfo.setOnClickListener(null);
            swipeBack();
            mState = SwipeState.NORMAL;
            // let swipe adapter handle canceled swipe
            dispatchOnSwipeLeftUndoClicked();
        }
    };

    private final OnClickListener rightUndoClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showUndoAction(false);
            mSwipeInfo.setOnClickListener(null);
            swipeBack();
            mState = SwipeState.NORMAL;
            // let swipe adapter handle canceled swipe
            dispatchOnSwipeRightUndoClicked();
        }
    };

    public SwipeItem(Context context) {
        this(context, null);
    }

    public SwipeItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // scroll threshold
        ViewConfiguration vc = ViewConfiguration.get(this.getContext());
        mTouchSlop = vc.getScaledTouchSlop();

        mDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFirstLayout = true;
        mPreviousPosition = 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFirstLayout = true;
        mPreviousPosition = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h != oldh) {
            mFirstLayout = true;
            mPreviousPosition = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // measure childs
        mSwipeInfo = getChildAt(0);
        mSwipeUndo = getChildAt(1);
        mSwipeItem = getChildAt(2);
        if (mFirstLayout) {
            switch (mState) {
                case LEFT_UNDO:
                    mSwipeInfo.setVisibility(GONE);
                    mSwipeUndo.setVisibility(VISIBLE);
                    break;
                case RIGHT_UNDO:
                    mSwipeInfo.setVisibility(GONE);
                    mSwipeUndo.setVisibility(VISIBLE);
                    break;
                case NORMAL:
                    mSwipeUndo.setVisibility(GONE);
                    mSwipeInfo.setVisibility(VISIBLE);
            }
            ViewCompat.setAlpha(mSwipeInfo, 1);
            ViewCompat.setAlpha(mSwipeUndo, 1);
        }

        measureChildWithMargins(mSwipeInfo, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(mSwipeUndo, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(mSwipeItem, widthMeasureSpec, 0, heightMeasureSpec, 0);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mSwipeItem.getMeasuredHeight());

        mSwipeInfo.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        mSwipeUndo.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        mSwipeItem.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = right - left - getPaddingRight();
        final int parentTop = getPaddingTop();

        if (mFirstLayout) {
            // restore state
            int childLeft = parentLeft;
            int childRight = parentRight;
            switch (mState) {
                case LEFT_UNDO:
                    childLeft -= mHorizontalDragRange;
                    childRight -= mHorizontalDragRange;
                    break;
                case RIGHT_UNDO:
                    childLeft += mHorizontalDragRange;
                    childRight += mHorizontalDragRange;
                    break;
            }
            mHorizontalDragRange = getMeasuredWidth();
            mSwipeItem.layout(childLeft, parentTop, childRight, parentTop + mSwipeItem.getMeasuredHeight());
            mFirstLayout = false;
        }

        if (mSwipeInfo.getVisibility() != GONE)
            mSwipeInfo.layout(parentLeft, parentTop, parentRight, parentTop + mSwipeItem.getMeasuredHeight());
        if (mSwipeUndo.getVisibility() != GONE)
            mSwipeUndo.layout(parentLeft, parentTop, parentRight, parentTop + mSwipeItem.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        // handle parent scroll behaviour
        if (Math.abs(mSwipeItem.getLeft()) > mTouchSlop) {
            // disable parent scrolling
            ViewParent parent = getParent();
            if (parent != null) parent.requestDisallowInterceptTouchEvent(true);
        } else if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_UP || MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_CANCEL) {
            // enable parent scrolling
            ViewParent parent = getParent();
            if (parent != null) parent.requestDisallowInterceptTouchEvent(false);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setSwipeBackgroundColor(int resolvedColor) {
        setBackgroundColor(resolvedColor);
    }

    public void setSwipeLeftImageResource(int resId) {
        ((ImageView) findViewById(R.id.imageViewLeft)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageViewRight)).setImageResource(resId);
    }

    public void setSwipeRightImageResource(int resId) {
        ((ImageView) findViewById(R.id.imageViewLeft)).setImageResource(resId);
        ((ImageView) findViewById(R.id.imageViewRight)).setImageResource(0);
    }

    public void setSwipeDescription(CharSequence description) {
        ((TextView) findViewById(R.id.textViewDescription)).setText(description);
    }

    public void setSwipeUndoDescription(CharSequence description) {
        ((TextView) findViewById(R.id.undoDescription)).setText(description);
    }

    public void setSwipeDescriptionTextColor(int resolvedTextColor) {
        ((TextView) findViewById(R.id.textViewDescription)).setTextColor(resolvedTextColor);
        ((TextView) findViewById(R.id.undoDescription)).setTextColor(resolvedTextColor);
        ((TextView) findViewById(R.id.undoButton)).setTextColor(resolvedTextColor);
    }

    public void setSwipeConfiguration(SwipeConfiguration configuration) {
        mConfiguration = configuration;
        // TODO: handle configuration here if equal in both directions
    }

    protected void setSwipeState(SwipeState state) {
        mState = state;
        switch (mState) {
            case LEFT_UNDO:
                setSwipeBackgroundColor(mConfiguration.getLeftBackgroundColor());
                setSwipeUndoDescription(mConfiguration.getLeftUndoDescription());
                setSwipeDescriptionTextColor(mConfiguration.getLeftDescriptionTextColor());
                findViewById(R.id.undoButton).setOnClickListener(leftUndoClickListener);
                break;
            case RIGHT_UNDO:
                setSwipeBackgroundColor(mConfiguration.getRightBackgroundColor());
                setSwipeUndoDescription(mConfiguration.getRightUndoDescription());
                setSwipeDescriptionTextColor(mConfiguration.getRightDescriptionTextColor());
                findViewById(R.id.undoButton).setOnClickListener(rightUndoClickListener);
                break;
        }
    }

    public void setSwipeListener(OnSwipeListener listener) {
        mOnSwipeListener = listener;
    }

    public interface OnSwipeListener {
        /**
         * Called when the SwipeItem was swiped away to the left
         */
        void onSwipeLeft();

        /**
         * Called when the SwipeItem was swiped away to the right
         */
        void onSwipeRight();

        /**
         * Called when the SwipeItem was swiped away to the left and an undo action is started
         */
        void onSwipeLeftUndoStarted();

        /**
         * Called when the SwipeItem was swiped away to the right and an undo action is started
         */
        void onSwipeRightUndoStarted();

        /**
         * Called when the SwipeItem was swiped away to the left and undo was clicked
         */
        void onSwipeLeftUndoClicked();

        /**
         * Called when the SwipeItem was swiped away to the right and undo was clicked
         */
        void onSwipeRightUndoClicked();
    }

    void dispatchOnSwipeLeft() {
        if (mOnSwipeListener != null && mConfiguration.isLeftCallbackEnabled()) {
            mOnSwipeListener.onSwipeLeft();
        }
    }

    void dispatchOnSwipeRight() {
        if (mOnSwipeListener != null && mConfiguration.isRightCallbackEnabled()) {
            mOnSwipeListener.onSwipeRight();
        }
    }

    void dispatchOnSwipeLeftUndoStarted() {
        if (mOnSwipeListener != null && mConfiguration.isLeftCallbackEnabled()) {
            mOnSwipeListener.onSwipeLeftUndoStarted();
        }
    }

    void dispatchOnSwipeRightUndoStarted() {
        if (mOnSwipeListener != null && mConfiguration.isRightCallbackEnabled()) {
            mOnSwipeListener.onSwipeRightUndoStarted();
        }
    }

    void dispatchOnSwipeLeftUndoClicked() {
        if (mOnSwipeListener != null && mConfiguration.isLeftCallbackEnabled()) {
            mOnSwipeListener.onSwipeLeftUndoClicked();
        }
    }

    void dispatchOnSwipeRightUndoClicked() {
        if (mOnSwipeListener != null && mConfiguration.isRightCallbackEnabled()) {
            mOnSwipeListener.onSwipeRightUndoClicked();
        }
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == mSwipeItem;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left < 0) {
                return child.getLeft() + Math.round(mConfiguration.getLeftSwipeRange() * dx);
            } else {
                return child.getLeft() + Math.round(mConfiguration.getRightSwipeRange() * dx);
            }
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
                } else if (mSwipeItem.getLeft() == 0) {
                    // check whether settled from restricted swipe
                    if (mConfiguration.getLeftSwipeRange() != 1.0f && mHasPassedLeftThreshold) {
                        mHasPassedLeftThreshold = false;
                        dispatchOnSwipeLeft();
                    }
                    if (mConfiguration.getRightSwipeRange() != 1.0f && mHasPassedRightThreshold) {
                        mHasPassedRightThreshold = false;
                        dispatchOnSwipeRight();
                    }
                }
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            handlePositionChange(left);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // logic for slide behaviour
            if (xvel < 0 && mConfiguration.getLeftSwipeRange() == 1.0f) {
                // dismiss to left
                mDragHelper.settleCapturedViewAt(-mHorizontalDragRange, releasedChild.getTop());
            } else if (xvel > 0 && mConfiguration.getRightSwipeRange() == 1.0f) {
                // dismiss to right
                mDragHelper.settleCapturedViewAt(mHorizontalDragRange, releasedChild.getTop());
            } else {
                // not enough velocity to dismiss
                mDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
            }

            invalidate();
        }
    }

    private void handlePositionChange(int newLeft) {
        if (newLeft > 0) {
            if (mPreviousPosition <= 0) {
                // show right action
                setSwipeBackgroundColor(mConfiguration.getRightBackgroundColor());
                setSwipeRightImageResource(mConfiguration.getRightDrawableResource());
                setSwipeDescription(mConfiguration.getRightDescription());
                setSwipeDescriptionTextColor(mConfiguration.getRightDescriptionTextColor());
            }
            float rightRange = mConfiguration.getRightSwipeRange();
            if (rightRange != 1.0f && newLeft > Math.round(mHorizontalDragRange * rightRange * 0.75f)) {
                mHasPassedRightThreshold = true;
                mHasPassedLeftThreshold = false;
            }
        } else if (newLeft < 0) {
            if (mPreviousPosition >= 0) {
                // show left action
                setSwipeBackgroundColor(mConfiguration.getLeftBackgroundColor());
                setSwipeLeftImageResource(mConfiguration.getLeftDrawableResource());
                setSwipeDescription(mConfiguration.getLeftDescription());
                setSwipeDescriptionTextColor(mConfiguration.getLeftDescriptionTextColor());
            }
            float leftRange = mConfiguration.getLeftSwipeRange();
            if (leftRange != 1.0f && newLeft < (-mHorizontalDragRange * leftRange * 0.75f)) {
                mHasPassedLeftThreshold = true;
                mHasPassedRightThreshold = false;
            }
        }
        mPreviousPosition = newLeft;
    }

    private void handleLeftSwipe() {
        if (mConfiguration.isLeftUndoable()) {
            mState = SwipeState.LEFT_UNDO;
            setSwipeUndoDescription(mConfiguration.getLeftUndoDescription());
            showUndoAction(true);
            mSwipeUndo.findViewById(R.id.undoButton).setOnClickListener(leftUndoClickListener);
            // let swipe adapter handle started swipe with undo action
            dispatchOnSwipeLeftUndoStarted();
        } else {
            dispatchOnSwipeLeft();
        }
    }

    private void handleRightSwipe() {
        if (mConfiguration.isRightUndoable()) {
            mState = SwipeState.RIGHT_UNDO;
            setSwipeUndoDescription(mConfiguration.getRightUndoDescription());
            showUndoAction(true);
            mSwipeUndo.findViewById(R.id.undoButton).setOnClickListener(rightUndoClickListener);
            // let swipe adapter handle started swipe with undo action
            dispatchOnSwipeRightUndoStarted();
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
        ViewCompat.setAlpha(mSwipeUndo, show ? 0 : 1);
        ViewCompat.setAlpha(mSwipeInfo, show ? 1 : 0);
        if (show) mSwipeUndo.setVisibility(VISIBLE);
        if (!show) mSwipeInfo.setVisibility(VISIBLE);
        final ViewPropertyAnimatorCompat undoAnimation = ViewCompat.animate(mSwipeUndo);
        undoAnimation.setDuration(300)
                .alpha(show ? 1 : 0).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
            }

            @Override
            public void onAnimationEnd(View view) {
                undoAnimation.setListener(null);
                ViewCompat.setAlpha(view, show ? 1 : 0);
                if (!show) view.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(View view) {
            }
        }).start();
        final ViewPropertyAnimatorCompat infoAnimation = ViewCompat.animate(mSwipeInfo);
        infoAnimation.setDuration(300)
                .alpha(show ? 0 : 1).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
            }

            @Override
            public void onAnimationEnd(View view) {
                infoAnimation.setListener(null);
                ViewCompat.setAlpha(view, show ? 0 : 1);
                if (show) view.setVisibility(GONE);
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
