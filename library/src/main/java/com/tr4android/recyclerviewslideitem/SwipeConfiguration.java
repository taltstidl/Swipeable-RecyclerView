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
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class SwipeConfiguration {
    // drawable resId
    private int mLeftDrawableResource = 0;
    private int mRightDrawableResource = 0;
    // description text
    private CharSequence mLeftDescription;
    private CharSequence mRightDescription;
    // description text color
    private int mLeftDescriptionTextColor;
    private int mRightDescriptionTextColor;
    // background color
    private int mLeftBackgroundColor;
    private int mRightBackgroundColor;
    // whether callback is enabled when swiping
    private boolean mLeftCallbackEnabled = true;
    private boolean mRightCallbackEnabled = true;
    // whether action after swiping is undoable
    private boolean mLeftUndoable;
    private boolean mRightUndoable;
    // undo description text
    private CharSequence mLeftUndoDescription;
    private CharSequence mRightUndoDescription;
    // undo button text
    private CharSequence mLeftUndoButtonText;
    private CharSequence mRightUndoButtonText;
    // swipe range (value between 0 and 1.0)
    private float mLeftSwipeRange;
    private float mRightSwipeRange;
    // interpolator used for swipe animation
    private Interpolator mLeftSwipeInterpolator;
    private Interpolator mRightSwipeInterpolator;
    // Undo duration in ms
    private long mLeftUndoDuration = 5000;
    private long mRightUndoDuration = 5000;

    int getLeftDrawableResource() {
        return mLeftDrawableResource;
    }

    void setLeftDrawableResource(int resId) {
        mLeftDrawableResource = resId;
    }

    int getRightDrawableResource() {
        return mRightDrawableResource;
    }

    void setRightDrawableResource(int resId) {
        mRightDrawableResource = resId;
    }

    CharSequence getLeftDescription() {
        return mLeftDescription;
    }

    void setLeftDescription(CharSequence description) {
        mLeftDescription = description;
    }

    CharSequence getRightDescription() {
        return mRightDescription;
    }

    void setRightDescription(CharSequence description) {
        mRightDescription = description;
    }

    int getLeftDescriptionTextColor() {
        return mLeftDescriptionTextColor;
    }

    void setLeftDescriptionTextColor(int color) {
        mLeftDescriptionTextColor = color;
    }

    int getRightDescriptionTextColor() {
        return mRightDescriptionTextColor;
    }

    void setRightDescriptionTextColor(int color) {
        mRightDescriptionTextColor = color;
    }

    int getLeftBackgroundColor() {
        return mLeftBackgroundColor;
    }

    void setLeftBackgroundColor(int color) {
        mLeftBackgroundColor = color;
    }

    int getRightBackgroundColor() {
        return mRightBackgroundColor;
    }

    void setRightBackgroundColor(int color) {
        mRightBackgroundColor = color;
    }

    boolean isLeftCallbackEnabled() {
        return mLeftCallbackEnabled;
    }

    void setLeftCallbackEnabled(boolean enabled) {
        mLeftCallbackEnabled = enabled;
    }

    boolean isRightCallbackEnabled() {
        return mRightCallbackEnabled;
    }

    void setRightCallbackEnabled(boolean enabled) {
        mRightCallbackEnabled = enabled;
    }

    boolean isLeftUndoable() {
        return mLeftUndoable;
    }

    void setLeftUndoable(boolean undoable) {
        mLeftUndoable = undoable;
    }

    boolean isRightUndoable() {
        return mRightUndoable;
    }

    void setRightUndoable(boolean undoable) {
        mRightUndoable = undoable;
    }

    CharSequence getLeftUndoDescription() {
        return mLeftUndoDescription;
    }

    void setLeftUndoDescription(CharSequence description) {
        mLeftUndoDescription = description;
    }

    CharSequence getRightUndoDescription() {
        return mRightUndoDescription;
    }

    void setRightUndoDescription(CharSequence description) {
        mRightUndoDescription = description;
    }

    CharSequence getLeftUndoButtonText() {
        return mLeftUndoButtonText;
    }

    void setLeftUndoButtonText(CharSequence text) {
        mLeftUndoButtonText = text;
    }

    CharSequence getRightUndoButtonText() {
        return mRightUndoButtonText;
    }

    void setRightUndoButtonText(CharSequence text) {
        mRightUndoButtonText = text;
    }

    float getLeftSwipeRange() {
        return mLeftSwipeRange;
    }

    Interpolator getLeftSwipeInterpolator() {
        return mLeftSwipeInterpolator;
    }

    void setLeftSwipeBehaviour(float range, Interpolator interpolator) {
        mLeftSwipeRange = range;
        mLeftSwipeInterpolator = interpolator;
    }

    float getRightSwipeRange() {
        return mRightSwipeRange;
    }

    Interpolator getRightSwipeInterpolator() {
        return mRightSwipeInterpolator;
    }

    void setRightSwipeBehaviour(float range, Interpolator interpolator) {
        mRightSwipeRange = range;
        mRightSwipeInterpolator = interpolator;
    }

    public long getLeftUndoDuration() {
        return mLeftUndoDuration;
    }

    public void setLeftUndoDuration(long mLeftUndoDuration) {
        this.mLeftUndoDuration = mLeftUndoDuration;
    }

    public long getRightUndoDuration() {
        return mRightUndoDuration;
    }

    public void setRightUndoDuration(long mRightUndoDuration) {
        this.mRightUndoDuration = mRightUndoDuration;
    }


    /**
     * Class for default swipe behaviour provided by the library
     */
    public static class SwipeBehaviour {
        // default interpolators
        private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
        private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

        // default SwipeBehaviour values
        public static final SwipeBehaviour NORMAL_SWIPE = new SwipeBehaviour(1.0f, LINEAR_INTERPOLATOR);
        public static final SwipeBehaviour RESTRICTED_SWIPE = new SwipeBehaviour(0.25f, DECELERATE_INTERPOLATOR);
        public static final SwipeBehaviour NO_SWIPE = new SwipeBehaviour(0.0f, LINEAR_INTERPOLATOR);

        public float range;
        public Interpolator interpolator;

        SwipeBehaviour(float range, Interpolator interpolator) {
            this.range = range;
            this.interpolator = interpolator;
        }
    }

    /**
     * Builder class for building SwipeConfiguration
     */
    public static class Builder {
        private Context mContext;
        private int mLeftDrawableResource = 0;
        private int mRightDrawableResource = 0;
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
        private long mLeftUndoDuration;
        private long mRightUndoDuration;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setLeftDrawableResource(@DrawableRes int resId) {
            mLeftDrawableResource = resId;
            return this;
        }

        public Builder setRightDrawableResource(@DrawableRes int resId) {
            mRightDrawableResource = resId;
            return this;
        }

        public Builder setDrawableResource(@DrawableRes int resId) {
            mLeftDrawableResource = resId;
            mRightDrawableResource = resId;
            return this;
        }

        public Builder setLeftDescription(CharSequence description) {
            mLeftDescription = description;
            return this;
        }

        public Builder setLeftDescription(@StringRes int resId) {
            mLeftDescription = mContext.getString(resId);
            return this;
        }

        public Builder setRightDescription(CharSequence description) {
            mRightDescription = description;
            return this;
        }

        public Builder setRightDescription(@StringRes int resId) {
            mRightDescription = mContext.getString(resId);
            return this;
        }

        public Builder setDescription(CharSequence description) {
            mLeftDescription = description;
            mRightDescription = description;
            return this;
        }

        public Builder setDescription(@StringRes int resId) {
            mLeftDescription = mContext.getString(resId);
            mRightDescription = mContext.getString(resId);
            return this;
        }

        public Builder setLeftDescriptionTextColor(int color) {
            mLeftDescriptionTextColor = color;
            return this;
        }

        public Builder setLeftDescriptionTextColorResource(@ColorRes int resId) {
            mLeftDescriptionTextColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setRightDescriptionTextColor(int color) {
            mRightDescriptionTextColor = color;
            return this;
        }

        public Builder setRightDescriptionTextColorResource(@ColorRes int resId) {
            mRightDescriptionTextColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setDescriptionTextColor(int color) {
            mLeftDescriptionTextColor = color;
            mRightDescriptionTextColor = color;
            return this;
        }

        public Builder setDescriptionTextColorResource(@ColorRes int resId) {
            mLeftDescriptionTextColor = mContext.getResources().getColor(resId);
            mRightDescriptionTextColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setLeftBackgroundColor(int color) {
            mLeftBackgroundColor = color;
            return this;
        }

        public Builder setLeftBackgroundColorResource(@ColorRes int resId) {
            mLeftBackgroundColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setRightBackgroundColor(int color) {
            mRightBackgroundColor = color;
            return this;
        }

        public Builder setRightBackgroundColorResource(@ColorRes int resId) {
            mRightBackgroundColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setBackgroundColor(int color) {
            mLeftBackgroundColor = color;
            mRightBackgroundColor = color;
            return this;
        }

        public Builder setBackgroundColorResource(@ColorRes int resId) {
            mLeftBackgroundColor = mContext.getResources().getColor(resId);
            mRightBackgroundColor = mContext.getResources().getColor(resId);
            return this;
        }

        public Builder setLeftCallbackEnabled(boolean enabled) {
            mLeftCallbackEnabled = enabled;
            return this;
        }

        public Builder setRightCallbackEnabled(boolean enabled) {
            mRightCallbackEnabled = enabled;
            return this;
        }

        public Builder setCallbackEnabled(boolean enabled) {
            mLeftCallbackEnabled = enabled;
            mRightCallbackEnabled = enabled;
            return this;
        }

        public Builder setLeftUndoable(boolean undoable) {
            mLeftUndoable = undoable;
            return this;
        }

        public Builder setRightUndoable(boolean undoable) {
            mRightUndoable = undoable;
            return this;
        }

        public Builder setUndoable(boolean undoable) {
            mLeftUndoable = undoable;
            mRightUndoable = undoable;
            return this;
        }

        public Builder setLeftUndoDescription(CharSequence description) {
            mLeftUndoDescription = description;
            return this;
        }

        public Builder setLeftUndoDescription(@StringRes int resid) {
            mLeftUndoDescription = mContext.getString(resid);
            return this;
        }

        public Builder setRightUndoDescription(CharSequence description) {
            mRightUndoDescription = description;
            return this;
        }

        public Builder setRightUndoDescription(@StringRes int resId) {
            mRightUndoDescription = mContext.getString(resId);
            return this;
        }

        public Builder setUndoDescription(CharSequence description) {
            mLeftUndoDescription = description;
            mRightUndoDescription = description;
            return this;
        }

        public Builder setUndoDescription(@StringRes int resId) {
            mLeftUndoDescription = mContext.getString(resId);
            mRightUndoDescription = mContext.getString(resId);
            return this;
        }

        public Builder setLeftUndoButtonText(CharSequence text) {
            mLeftUndoButtonText = text;
            return this;
        }

        public Builder setLeftUndoButtonText(@StringRes int resId) {
            mLeftUndoButtonText = mContext.getString(resId);
            return this;
        }

        public Builder setRightUndoButtonText(CharSequence text) {
            mRightUndoButtonText = text;
            return this;
        }

        public Builder setRightUndoButtonText(@StringRes int resId) {
            mRightUndoButtonText = mContext.getString(resId);
            return this;
        }

        public Builder setUndoButtonText(CharSequence text) {
            mLeftUndoButtonText = text;
            mRightUndoButtonText = text;
            return this;
        }

        public Builder setUndoButtonText(@StringRes int resId) {
            mLeftUndoButtonText = mContext.getString(resId);
            mRightUndoButtonText = mContext.getString(resId);
            return this;
        }

        public Builder setLeftSwipeBehaviour(float range, Interpolator interpolator) {
            mLeftSwipeRange = range;
            mLeftSwipeInterpolator = interpolator;
            return this;
        }

        public Builder setLeftSwipeBehaviour(SwipeBehaviour swipeBehaviour) {
            mLeftSwipeRange = swipeBehaviour.range;
            mLeftSwipeInterpolator = swipeBehaviour.interpolator;
            return this;
        }

        public Builder setRightSwipeBehaviour(float range, Interpolator interpolator) {
            mRightSwipeRange = range;
            mRightSwipeInterpolator = interpolator;
            return this;
        }

        public Builder setRightSwipeBehaviour(SwipeBehaviour swipeBehaviour) {
            mRightSwipeRange = swipeBehaviour.range;
            mRightSwipeInterpolator = swipeBehaviour.interpolator;
            return this;
        }

        public Builder setSwipeBehaviour(float range, Interpolator interpolator) {
            mLeftSwipeRange = range;
            mLeftSwipeInterpolator = interpolator;
            mRightSwipeRange = range;
            mRightSwipeInterpolator = interpolator;
            return this;
        }

        public Builder setSwipeBehaviour(SwipeBehaviour swipeBehaviour) {
            mLeftSwipeRange = swipeBehaviour.range;
            mLeftSwipeInterpolator = swipeBehaviour.interpolator;
            mRightSwipeRange = swipeBehaviour.range;
            mRightSwipeInterpolator = swipeBehaviour.interpolator;
            return this;
        }

        public Builder setLeftUndoDuration(long duration) {
            mLeftUndoDuration = duration;
            return this;
        }

        public Builder setRightUndoDuration(long duration) {
            mRightUndoDuration = duration;
            return this;
        }

        public SwipeConfiguration build() {
            SwipeConfiguration config = new SwipeConfiguration();
            config.setLeftDrawableResource(mLeftDrawableResource);
            config.setRightDrawableResource(mRightDrawableResource);
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
            config.setLeftUndoDuration(mLeftUndoDuration);
            config.setRightUndoDuration(mRightUndoDuration);
            config.setRightUndoButtonText(mRightUndoButtonText);

            if (mLeftSwipeInterpolator == null) {
                setLeftSwipeBehaviour(SwipeBehaviour.NORMAL_SWIPE);
            }
            config.setLeftSwipeBehaviour(mLeftSwipeRange, mLeftSwipeInterpolator);
            if (mRightSwipeInterpolator == null) {
                setRightSwipeBehaviour(SwipeBehaviour.NORMAL_SWIPE);
            }
            config.setRightSwipeBehaviour(mRightSwipeRange, mRightSwipeInterpolator);
            return config;
        }
    }

}