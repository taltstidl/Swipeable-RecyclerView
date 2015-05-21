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
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class SwipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SWIPE_LEFT = -1;
    public static final int SWIPE_RIGHT = 1;
    public static final int TIME_POST_DELAYED = 5000; // in ms

    private Handler mHandler = new Handler();
    private final ArrayList<SwipeRunnable> mRunnables = new ArrayList<>();

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SwipeItem item = (SwipeItem) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swipe, parent, false);
        return onCreateSwipeViewHolder(item, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerView.ViewHolder swipeHolder = holder;
        final SwipeItem swipeItem = (SwipeItem) swipeHolder.itemView;
        SwipeConfiguration configuration = onCreateSwipeConfiguration(swipeItem.getContext(), position);
        swipeItem.setSwipeConfiguration(configuration);
        swipeItem.setSwipeListener(new SwipeItem.OnSwipeListener() {
            @Override
            public void onSwipeLeft() {
                onSwipe(swipeHolder.getAdapterPosition(), SWIPE_LEFT);
            }

            @Override
            public void onSwipeRight() {
                onSwipe(swipeHolder.getAdapterPosition(), SWIPE_RIGHT);
            }

            @Override
            public void onSwipeLeftUndoStarted() {
                final int position = swipeHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    SwipeRunnable runnable = new SwipeRunnable(SWIPE_LEFT) {
                        @Override
                        public void run() {
                            synchronized (mRunnables) {
                                onSwipe(mRunnables.indexOf(this), SWIPE_LEFT);
                            }
                        }
                    };
                    synchronized (mRunnables) {
                        mRunnables.set(position, runnable);
                        mHandler.postDelayed(runnable, TIME_POST_DELAYED);
                    }
                }
            }

            @Override
            public void onSwipeRightUndoStarted() {
                final int position = swipeHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    SwipeRunnable runnable = new SwipeRunnable(SWIPE_RIGHT) {
                        @Override
                        public void run() {
                            synchronized (mRunnables) {
                                onSwipe(mRunnables.indexOf(this), SWIPE_RIGHT);
                            }
                        }
                    };
                    synchronized (mRunnables) {
                        mRunnables.set(position, runnable);
                        mHandler.postDelayed(runnable, TIME_POST_DELAYED);
                    }
                }
            }

            @Override
            public void onSwipeLeftUndoClicked() {
                final int position = swipeHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    synchronized (mRunnables) {
                        Runnable runnable = mRunnables.set(position, null);
                        mHandler.removeCallbacks(runnable);
                    }
                }
            }

            @Override
            public void onSwipeRightUndoClicked() {
                final int position = swipeHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    synchronized (mRunnables) {
                        Runnable runnable = mRunnables.set(position, null);
                        mHandler.removeCallbacks(runnable);
                    }
                }
            }
        });
        // restore swipe state
        SwipeRunnable swipeRunnable = mRunnables.get(position);
        if (swipeRunnable != null) {
            swipeItem.setSwipeState(swipeRunnable.getDirection() == SWIPE_LEFT ? SwipeItem.SwipeState.LEFT_UNDO : SwipeItem.SwipeState.RIGHT_UNDO);
        } else {
            swipeItem.setSwipeState(SwipeItem.SwipeState.NORMAL);
        }
        onBindSwipeViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRunnables.size() == 0) {
            int count = getItemCount();
            for (int i = 0; i < count; i++) {
                mRunnables.add(null);
            }
        }
        registerAdapterDataObserver(new SwipeAdapterDataObserver());
    }

    public abstract RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, final int position);

    @Override
    public abstract int getItemCount();

    public abstract SwipeConfiguration onCreateSwipeConfiguration(Context context, int position);

    public abstract void onSwipe(int position, int direction);

    /**
     * Observer for synchronized updating of undo runnables
     */
    private class SwipeAdapterDataObserver extends RecyclerView.AdapterDataObserver {
        public void onChanged() {
            synchronized (mRunnables) {
                int size = mRunnables.size();
                int itemCount = getItemCount();
                if (itemCount > size) {
                    onItemRangeChanged(0, size);
                    onItemRangeInserted(size, itemCount - size);
                } else {
                    onItemRangeChanged(0, itemCount);
                    onItemRangeRemoved(itemCount, size - itemCount);
                }
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            synchronized (mRunnables) {
                for (int i = 0; i < itemCount; i++) {
                    Runnable r = mRunnables.set(positionStart + i, null);
                    if (r != null) {
                        mHandler.removeCallbacks(r);
                    }
                }
            }
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            synchronized (mRunnables) {
                for (int i = 0; i < itemCount; i++) {
                    mRunnables.add(positionStart, null);
                }
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            synchronized (mRunnables) {
                for (int i = 0; i < itemCount; i++) {
                    int c = fromPosition > toPosition ? i : 0;
                    mRunnables.set(toPosition + c, mRunnables.remove(fromPosition + c));
                }
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            synchronized (mRunnables) {
                for (int i = 0; i < itemCount; i++) {
                    Runnable r = mRunnables.remove(positionStart);
                    if (r != null) {
                        mHandler.removeCallbacks(r);
                    }
                }
            }
        }
    }
}