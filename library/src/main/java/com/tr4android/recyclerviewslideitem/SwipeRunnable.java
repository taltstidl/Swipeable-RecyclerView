package com.tr4android.recyclerviewslideitem;

/**
 * Created by ThomasR on 11.05.2015.
 */
public abstract class SwipeRunnable implements Runnable {
    int mDirection;

    protected SwipeRunnable(int direction) {
        mDirection = direction;
    }

    @Override
    public abstract void run();

    protected int getDirection() {
        return mDirection;
    }
}
