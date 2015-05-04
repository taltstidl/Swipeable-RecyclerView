package com.tr4android.recyclerviewslideitem;

/**
 * Created by ThomasR on 03.05.2015.
 */
public class SwipeConfiguration {
    // drawable resId
    int mDrawableResId = 0;
    // description text
    CharSequence mDescription;
    // description text color
    int mDescriptionTextColor;
    // background color
    int mBackgroundColor;
    // whether or not dismissing is allowed
    boolean mDismissable = true;

    public void setDrawableResId(int resId) {
        mDrawableResId = resId;
    }

    public void setDescription(CharSequence description) {
        mDescription = description;
    }

    public void setDescriptionTextColor(int resolvedColor) {
        mDescriptionTextColor = resolvedColor;
    }

    public void setBackgroundColor(int resolvedColor) {
        mBackgroundColor = resolvedColor;
    }

    public void setDismissable(boolean dismissable) {
        mDismissable = dismissable;
    }

    public int getDrawableResId() {
        return mDrawableResId;
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public int getDescriptionTextColor() {
        return mDescriptionTextColor;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public boolean getDismissable() {
        return mDismissable;
    }
}
