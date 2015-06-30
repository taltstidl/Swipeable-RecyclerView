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

import android.graphics.Canvas;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class SwipeItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final int TIME_FOLLOW_UP_ACTION = 5000; // in ms
    private Handler mHandler = new Handler();

    private SwipeAdapter mAdapter;

    SwipeItemTouchHelperCallback(SwipeAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        SwipeConfiguration config = ((SwipeViewHolder) viewHolder).getSwipeConfiguration();
        int left = config.getLeftSwipeRange() == 0 ? 0 : ItemTouchHelper.LEFT;
        int right = config.getRightSwipeRange() == 0 ? 0 : ItemTouchHelper.RIGHT;
        int swipeFlags = left | right;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int direction) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mAdapter.onSwipe(viewHolder, direction);
            }
        };
        mHandler.postDelayed(runnable, TIME_FOLLOW_UP_ACTION);
    }

    /*@Override
    public float getSwipeTreshold(RecyclerView.ViewHolder viewHolder){

    }*/

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        getDefaultUIUtil().clearView(((SwipeViewHolder) viewHolder).getSwipeItem());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(((SwipeViewHolder) viewHolder).getSwipeItem());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        float width = viewHolder.itemView.getWidth();
        SwipeConfiguration config = ((SwipeViewHolder) viewHolder).getSwipeConfiguration();
        if (dX == width) {
            if (config.isRightUndoable()) {
                ((SwipeViewHolder) viewHolder).setState(SwipeViewHolder.SwipeState.STATE_ACTION_RIGHT);
            }
        } else if (dX == -width) {
            if (config.isLeftUndoable()) {
                ((SwipeViewHolder) viewHolder).setState(SwipeViewHolder.SwipeState.STATE_ACTION_LEFT);
            }
        } else if (dX > 0) {
            float range = config.getRightSwipeRange();
            dX *= range;
            float max = range * width;
            if (max == dX) {
                mAdapter.onSwipe(viewHolder, ItemTouchHelper.RIGHT);
            }
            ((SwipeViewHolder) viewHolder).setState(SwipeViewHolder.SwipeState.STATE_SWIPE_RIGHT);
        } else {
            float range = config.getLeftSwipeRange();
            dX *= range;
            float min = -range * width;
            if (min == dX) {
                mAdapter.onSwipe(viewHolder, ItemTouchHelper.LEFT);
            }
            ((SwipeViewHolder) viewHolder).setState(SwipeViewHolder.SwipeState.STATE_SWIPE_LEFT);
        }
        Log.w("SwipeItemTouchCallback", "dX: " + dX + " @width " + viewHolder.itemView.getWidth());
        getDefaultUIUtil().onDraw(c, recyclerView, ((SwipeViewHolder) viewHolder).getSwipeItem(), dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDrawOver(c, recyclerView, ((SwipeViewHolder) viewHolder).getSwipeItem(), dX, dY, actionState, isCurrentlyActive);
    }
}