package com.tr4android.recyclerviewslideitem;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Copyright (c) 2015 TR4Android
 */

public class SwipeConfiguration {
    // left drawable resId
    private int mLeftDrawableResId = 0;
    // right drawable resId
    private int mRightDrawableResId = 0;
    // left description text
    private CharSequence mLeftDescription;
    // right description text
    private CharSequence mRightDescription;
    // left description text color
    private int mLeftDescriptionTextColor;
    // right description text color
    private int mRightDescriptionTextColor;
    // left background color
    private int mLeftBackgroundColor;
    // right background color
    private int mRightBackgroundColor;
    // whether callback is enabled when swiping from left
    private boolean mLeftCallbackEnabled = true;
    // whether callback is enabled when swiping from right
    private boolean mRightCallbackEnabled = true;
    // whether action after swiping from left is undoable
    private boolean mLeftUndoable;
    // whether action after swiping from right is undoable
    private boolean mRightUndoable;
    // left undo description text
    private CharSequence mLeftUndoDescription;
    // right undo description text
    private CharSequence mRightUndoDescription;
    // left undo button text
    private CharSequence mLeftUndoButtonText;
    // right undo button text
    private CharSequence mRightUndoButtonText;
    // right swipe range (value between 0 and 1.0)
    private float mLeftSwipeRange;
    // right swipe range (value between 0 and 1.0)
    private float mRightSwipeRange;
    // right interpolator used for swipe animation
    private Interpolator mLeftSwipeInterpolator;
    // right interpolator used for swipe animation
    private Interpolator mRightSwipeInterpolator;

    int getLeftDrawableResId() {
        return mLeftDrawableResId;
    }

    void setLeftDrawableResId(int leftDrawableResId) {
        mLeftDrawableResId = leftDrawableResId;
    }

    int getRightDrawableResId() {
        return mRightDrawableResId;
    }

    void setRightDrawableResId(int rightDrawableResId) {
        mRightDrawableResId = rightDrawableResId;
    }

    CharSequence getLeftDescription() {
        return mLeftDescription;
    }

    void setLeftDescription(CharSequence leftDescription) {
        mLeftDescription = leftDescription;
    }

    CharSequence getRightDescription() {
        return mRightDescription;
    }

    void setRightDescription(CharSequence rightDescription) {
        mRightDescription = rightDescription;
    }

    int getLeftDescriptionTextColor() {
        return mLeftDescriptionTextColor;
    }

    void setLeftDescriptionTextColor(int leftDescriptionTextColor) {
        mLeftDescriptionTextColor = leftDescriptionTextColor;
    }

    int getRightDescriptionTextColor() {
        return mRightDescriptionTextColor;
    }

    void setRightDescriptionTextColor(int rightDescriptionTextColor) {
        mRightDescriptionTextColor = rightDescriptionTextColor;
    }

    int getLeftBackgroundColor() {
        return mLeftBackgroundColor;
    }

    void setLeftBackgroundColor(int leftBackgroundColor) {
        mLeftBackgroundColor = leftBackgroundColor;
    }

    int getRightBackgroundColor() {
        return mRightBackgroundColor;
    }

    void setRightBackgroundColor(int rightBackgroundColor) {
        mRightBackgroundColor = rightBackgroundColor;
    }

    boolean isLeftCallbackEnabled() {
        return mLeftCallbackEnabled;
    }

    void setLeftCallbackEnabled(boolean leftCallbackEnabled) {
        mLeftCallbackEnabled = leftCallbackEnabled;
    }

    boolean isRightCallbackEnabled() {
        return mRightCallbackEnabled;
    }

    void setRightCallbackEnabled(boolean rightCallbackEnabled) {
        mRightCallbackEnabled = rightCallbackEnabled;
    }

    boolean isLeftUndoable() {
        return mLeftUndoable;
    }

    void setLeftUndoable(boolean leftUndoable) {
        mLeftUndoable = leftUndoable;
    }

    boolean isRightUndoable() {
        return mRightUndoable;
    }

    void setRightUndoable(boolean rightUndoable) {
        mRightUndoable = rightUndoable;
    }

    CharSequence getLeftUndoDescription() {
        return mLeftUndoDescription;
    }

    void setLeftUndoDescription(CharSequence leftUndoDescription) {
        mLeftUndoDescription = leftUndoDescription;
    }

    CharSequence getRightUndoDescription() {
        return mRightUndoDescription;
    }

    void setRightUndoDescription(CharSequence rightUndoDescription) {
        mRightUndoDescription = rightUndoDescription;
    }

    CharSequence getLeftUndoButtonText() {
        return mLeftUndoButtonText;
    }

    void setLeftUndoButtonText(CharSequence leftUndoButtonText) {
        mLeftUndoButtonText = leftUndoButtonText;
    }

    CharSequence getRightUndoButtonText() {
        return mRightUndoButtonText;
    }

    void setRightUndoButtonText(CharSequence rightUndoButtonText) {
        mRightUndoButtonText = rightUndoButtonText;
    }

    float getLeftSwipeRange() {
        return mLeftSwipeRange;
    }

    Interpolator getLeftSwipeInterpolator() {
        return mLeftSwipeInterpolator;
    }

    void setLeftSwipeBehaviour(float leftSwipeRange, Interpolator leftSwipeInterpolator) {
        mLeftSwipeRange = leftSwipeRange;
        mLeftSwipeInterpolator = leftSwipeInterpolator;
    }

    float getRightSwipeRange() {
        return mRightSwipeRange;
    }

    Interpolator getRightSwipeInterpolator() {
        return mRightSwipeInterpolator;
    }

    void setRightSwipeBehavior(float rightSwipeRange, Interpolator rightSwipeInterpolator) {
        mRightSwipeRange = rightSwipeRange;
        mRightSwipeInterpolator = rightSwipeInterpolator;
    }

    /**
     * Class for default swipe behaviour provided by the library
     */
    public static class SwipeBehavior {
        // default interpolators
        private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
        private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

        // default SwipeBehaviour values
        public static final SwipeBehavior NORMAL_SWIPE = new SwipeBehavior(1.0f, LINEAR_INTERPOLATOR);
        public static final SwipeBehavior RESTRICTED_SWIPE = new SwipeBehavior(0.25f, DECELERATE_INTERPOLATOR);

        public float range;
        public Interpolator interpolator;

        SwipeBehavior(float range, Interpolator interpolator) {
            this.range = range;
            this.interpolator = interpolator;
        }
    }

    /**
     * Builder class for building SwipeConfiguration
     */
    public static class Builder {
        private Context mContext;
        private int mLeftDrawableResId = 0;
        private int mRightDrawableResId = 0;
        private CharSequence mLeftDescription;
        private CharSequence mRightDescription;
        private int mLeftDescriptionTextColor;
        private int mRightDescriptionTextColor;
        private int mLeftBackgroundColor;
        private int mRightBackgroundColor;
        private boolean mLeftCallbackEnabled = true;
        private boolean mRightCallbackEnabled = true;
        private boolean mLeftUndoable;
        private boolean mRightUndoable;
        private CharSequence mLeftUndoDescription;
        private CharSequence mRightUndoDescription;
        private CharSequence mLeftUndoButtonText;
        private CharSequence mRightUndoButtonText;
        private float mLeftSwipeRange;
        private float mRightSwipeRange;
        private Interpolator mLeftSwipeInterpolator;
        private Interpolator mRightSwipeInterpolator;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setLeftDrawableResId(@DrawableRes int leftDrawableResId) {
            mLeftDrawableResId = leftDrawableResId;
            return this;
        }

        public Builder setRightDrawableResId(@DrawableRes int rightDrawableResId) {
            mRightDrawableResId = rightDrawableResId;
            return this;
        }

        public Builder setDrawableResId(@DrawableRes int drawableResId) {
            mLeftDrawableResId = drawableResId;
            mRightDrawableResId = drawableResId;
            return this;
        }

        public Builder setLeftDescription(CharSequence leftDescription) {
            mLeftDescription = leftDescription;
            return this;
        }

        public Builder setLeftDescription(@StringRes int resid) {
            mLeftDescription = mContext.getString(resid);
            return this;
        }

        public Builder setRightDescription(CharSequence rightDescription) {
            mRightDescription = rightDescription;
            return this;
        }

        public Builder setRightDescription(@StringRes int resid) {
            mRightDescription = mContext.getString(resid);
            return this;
        }

        public Builder setDescription(CharSequence description) {
            mLeftDescription = description;
            mRightDescription = description;
            return this;
        }

        public Builder setDescription(@StringRes int resid) {
            mLeftDescription = mContext.getString(resid);
            mRightDescription = mContext.getString(resid);
            return this;
        }

        public Builder setLeftDescriptionTextColor(int leftDescriptionTextColor) {
            mLeftDescriptionTextColor = leftDescriptionTextColor;
            return this;
        }

        public Builder setLeftDescriptionTextColorResId(@ColorRes int resId) {
            mLeftDescriptionTextColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setRightDescriptionTextColor(int rightDescriptionTextColor) {
            mRightDescriptionTextColor = rightDescriptionTextColor;
            return this;
        }

        public Builder setRightDescriptionTextColorId(@ColorRes int resId) {
            mRightDescriptionTextColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setDescriptionTextColor(int descriptionTextColor) {
            mLeftDescriptionTextColor = descriptionTextColor;
            mRightDescriptionTextColor = descriptionTextColor;
            return this;
        }

        public Builder setDescriptionTextColorId(@ColorRes int resId) {
            mLeftDescriptionTextColor = mContext.getResources().getColor(resId);
            mRightDescriptionTextColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setLeftBackgroundColor(int leftBackgroundColor) {
            mLeftBackgroundColor = leftBackgroundColor;
            return this;
        }

        public Builder setLeftBackgroundColorId(@ColorRes int resId) {
            mLeftBackgroundColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setRightBackgroundColor(int rightBackgroundColor) {
            mRightBackgroundColor = rightBackgroundColor;
            return this;
        }

        public Builder setRightBackgroundColorId(@ColorRes int resId) {
            mRightBackgroundColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            mLeftBackgroundColor = backgroundColor;
            mRightBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setBackgroundColorId(@ColorRes int resId) {
            mLeftBackgroundColor = mContext.getResources().getColor(resId);
            mRightBackgroundColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setLeftCallbackEnabled(boolean leftCallbackEnabled) {
            mLeftCallbackEnabled = leftCallbackEnabled;
            return this;
        }

        public Builder setRightCallbackEnabled(boolean rightCallbackEnabled) {
            mRightCallbackEnabled = rightCallbackEnabled;
            return this;
        }

        public Builder setCallbackEnabled(boolean callbackEnabled) {
            mLeftCallbackEnabled = callbackEnabled;
            mRightCallbackEnabled = callbackEnabled;
            return this;
        }

        public Builder setLeftUndoable(boolean leftUndoable) {
            mLeftUndoable = leftUndoable;
            return this;
        }

        public Builder setRightUndoable(boolean rightUndoable) {
            mRightUndoable = rightUndoable;
            return this;
        }

        public Builder setUndoable(boolean undoable) {
            mLeftUndoable = undoable;
            mRightUndoable = undoable;
            return this;
        }

        public Builder setLeftUndoDescription(CharSequence leftUndoDescription) {
            mLeftUndoDescription = leftUndoDescription;
            return this;
        }

        public Builder setLeftUndoDescription(@StringRes int resid) {
            mLeftUndoDescription = mContext.getString(resid);
            return this;
        }

        public Builder setRightUndoDescription(CharSequence rightUndoDescription) {
            mRightUndoDescription = rightUndoDescription;
            return this;
        }

        public Builder setRightUndoDescription(@StringRes int resid) {
            mRightUndoDescription = mContext.getString(resid);
            return this;
        }

        public Builder setUndoDescription(CharSequence undoDescription) {
            mLeftUndoDescription = undoDescription;
            mRightUndoDescription = undoDescription;
            return this;
        }

        public Builder setUndoDescription(@StringRes int resid) {
            mLeftUndoDescription = mContext.getString(resid);
            mRightUndoDescription = mContext.getString(resid);
            return this;
        }

        public Builder setLeftUndoButtonText(CharSequence leftUndoButtonText) {
            mLeftUndoButtonText = leftUndoButtonText;
            return this;
        }

        public Builder setLeftUndoButtonText(@StringRes int resid) {
            mLeftUndoButtonText = mContext.getString(resid);
            return this;
        }

        public Builder setRightUndoButtonText(CharSequence rightUndoButtonText) {
            mRightUndoButtonText = rightUndoButtonText;
            return this;
        }

        public Builder setRightUndoButtonText(@StringRes int resid) {
            mRightUndoButtonText = mContext.getString(resid);
            return this;
        }

        public Builder setUndoButtonText(CharSequence undoButtonText) {
            mLeftUndoButtonText = undoButtonText;
            mRightUndoButtonText = undoButtonText;
            return this;
        }

        public Builder setUndoButtonText(@StringRes int resid) {
            mLeftUndoButtonText = mContext.getString(resid);
            mRightUndoButtonText = mContext.getString(resid);
            return this;
        }

        public Builder setLeftSwipeBehaviour(float leftSwipeRange, Interpolator leftSwipeInterpolator) {
            mLeftSwipeRange = leftSwipeRange;
            mLeftSwipeInterpolator = leftSwipeInterpolator;
            return this;
        }

        public Builder setLeftSwipeBehaviour(SwipeBehavior leftSwipeBehaviour) {
            mLeftSwipeRange = leftSwipeBehaviour.range;
            mLeftSwipeInterpolator = leftSwipeBehaviour.interpolator;
            return this;
        }

        public Builder setRightSwipeBehavior(float rightSwipeRange, Interpolator rightSwipeInterpolator) {
            mRightSwipeRange = rightSwipeRange;
            mRightSwipeInterpolator = rightSwipeInterpolator;
            return this;
        }

        public Builder setRightSwipeBehavior(SwipeBehavior rightSwipeBehavior) {
            mRightSwipeRange = rightSwipeBehavior.range;
            mRightSwipeInterpolator = rightSwipeBehavior.interpolator;
            return this;
        }

        public Builder setSwipeBehavior(float swipeRange, Interpolator swipeInterpolator) {
            mLeftSwipeRange = swipeRange;
            mLeftSwipeInterpolator = swipeInterpolator;
            mRightSwipeRange = swipeRange;
            mRightSwipeInterpolator = swipeInterpolator;
            return this;
        }

        public Builder setSwipeBehavior(SwipeBehavior swipeBehavior) {
            mLeftSwipeRange = swipeBehavior.range;
            mLeftSwipeInterpolator = swipeBehavior.interpolator;
            mRightSwipeRange = swipeBehavior.range;
            mRightSwipeInterpolator = swipeBehavior.interpolator;
            return this;
        }

        public SwipeConfiguration build() {
            SwipeConfiguration config = new SwipeConfiguration();
            config.setLeftDrawableResId(mLeftDrawableResId);
            config.setRightDrawableResId(mRightDrawableResId);
            config.setLeftDescription(mLeftDescription);
            config.setRightDescription(mRightDescription);
            config.setLeftDescriptionTextColor(mLeftDescriptionTextColor);
            config.setRightDescriptionTextColor(mRightDescriptionTextColor);
            config.setLeftBackgroundColor(mLeftBackgroundColor);
            config.setRightBackgroundColor(mRightBackgroundColor);
            config.setLeftCallbackEnabled(mLeftCallbackEnabled);
            config.setRightCallbackEnabled(mRightCallbackEnabled);
            config.setLeftUndoable(mLeftUndoable);
            config.setRightUndoable(mRightUndoable);
            config.setLeftUndoDescription(mLeftUndoDescription);
            config.setRightUndoDescription(mRightUndoDescription);
            config.setLeftUndoButtonText(mLeftUndoButtonText);
            config.setRightUndoButtonText(mRightUndoButtonText);
            config.setLeftSwipeBehaviour(mLeftSwipeRange, mLeftSwipeInterpolator);
            config.setRightSwipeBehavior(mRightSwipeRange, mRightSwipeInterpolator);
            return config;
        }
    }
}