package com.tr4android.recyclerviewslideitem;

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
    // whether or not dismissing is allowed when swiping from left
    private boolean mLeftDismissable = true;
    // whether or not dismissing is allowed when swiping from right
    private boolean mRightDismissable = true;
    // whether action after swiping from left is undoable
    private boolean mLeftUndoable;
    // whether action after swiping from right is undoable
    private boolean mRightUndoable;
    // left undo description text
    private CharSequence mLeftUndoDescription;
    // right undo description text
    private CharSequence mRightUndoDescription;


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

    boolean isLeftDismissable() {
        return mLeftDismissable;
    }

    void setLeftDismissable(boolean leftDismissable) {
        mLeftDismissable = leftDismissable;
    }

    boolean isRightDismissable() {
        return mRightDismissable;
    }

    void setRightDismissable(boolean rightDismissable) {
        mRightDismissable = rightDismissable;
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

    public CharSequence getLeftUndoDescription() {
        return mLeftUndoDescription;
    }

    public void setLeftUndoDescription(CharSequence leftUndoDescription) {
        mLeftUndoDescription = leftUndoDescription;
    }

    public CharSequence getRightUndoDescription() {
        return mRightUndoDescription;
    }

    public void setRightUndoDescription(CharSequence rightUndoDescription) {
        mRightUndoDescription = rightUndoDescription;
    }

    /**
     * Builder class for building SwipeConfiguration
     */
    public class Builder {
        private int mLeftDrawableResId = 0;
        private int mRightDrawableResId = 0;
        private CharSequence mLeftDescription;
        private CharSequence mRightDescription;
        private int mLeftDescriptionTextColor;
        private int mRightDescriptionTextColor;
        private int mLeftBackgroundColor;
        private int mRightBackgroundColor;
        private boolean mLeftDismissable = true;
        private boolean mRightDismissable = true;
        private boolean mLeftUndoable;
        private boolean mRightUndoable;
        private CharSequence mLeftUndoDescription;
        private CharSequence mRightUndoDescription;

        public Builder setLeftDrawableResId(int leftDrawableResId) {
            mLeftDrawableResId = leftDrawableResId;
            return this;
        }

        public Builder setRightDrawableResId(int rightDrawableResId) {
            mRightDrawableResId = rightDrawableResId;
            return this;
        }

        public Builder setDrawableResId(int drawableResId) {
            mLeftDrawableResId = drawableResId;
            mRightDrawableResId = drawableResId;
            return this;
        }

        public Builder setLeftDescription(CharSequence leftDescription) {
            mLeftDescription = leftDescription;
            return this;
        }

        public Builder setRightDescription(CharSequence rightDescription) {
            mRightDescription = rightDescription;
            return this;
        }

        public Builder setDescription(CharSequence description) {
            mLeftDescription = description;
            mRightDescription = description;
            return this;
        }

        public Builder setLeftDescriptionTextColor(int leftDescriptionTextColor) {
            mLeftDescriptionTextColor = leftDescriptionTextColor;
            return this;
        }

        public Builder setRightDescriptionTextColor(int rightDescriptionTextColor) {
            mRightDescriptionTextColor = rightDescriptionTextColor;
            return this;
        }

        public Builder setDescriptionTextColor(int descriptionTextColor) {
            mLeftDescriptionTextColor = descriptionTextColor;
            mRightDescriptionTextColor = descriptionTextColor;
            return this;
        }

        public Builder setLeftBackgroundColor(int leftBackgroundColor) {
            mLeftBackgroundColor = leftBackgroundColor;
            return this;
        }

        public Builder setRightBackgroundColor(int rightBackgroundColor) {
            mRightBackgroundColor = rightBackgroundColor;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            mLeftBackgroundColor = backgroundColor;
            mRightBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setLeftDismissable(boolean leftDismissable) {
            mLeftDismissable = leftDismissable;
            return this;
        }

        public Builder setRightDismissable(boolean rightDismissable) {
            mRightDismissable = rightDismissable;
            return this;
        }

        public Builder setDismissable(boolean dismissable) {
            mLeftDismissable = dismissable;
            mRightDismissable = dismissable;
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

        public Builder setRightUndoDescription(CharSequence rightUndoDescription) {
            mRightUndoDescription = rightUndoDescription;
            return this;
        }

        public Builder setUndoDescription(CharSequence undoDescription) {
            mLeftUndoDescription = undoDescription;
            mRightUndoDescription = undoDescription;
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
            config.setLeftDismissable(mLeftDismissable);
            config.setRightDismissable(mRightDismissable);
            config.setLeftUndoable(mLeftUndoable);
            config.setLeftUndoable(mRightUndoable);
            config.setLeftUndoDescription(mLeftUndoDescription);
            config.setRightUndoDescription(mRightUndoDescription);
            return config;
        }
    }
}

