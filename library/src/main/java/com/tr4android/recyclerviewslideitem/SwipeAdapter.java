package com.tr4android.recyclerviewslideitem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Copyright (c) 2015 TR4Android
 */
public abstract class SwipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SWIPE_LEFT = -1;
    public static final int SWIPE_RIGHT = 1;

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SwipeItem item = (SwipeItem) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swipe, parent, false);
        RecyclerView.ViewHolder holder = onCreateSwipeViewHolder(item, viewType);
        return holder;
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerView.ViewHolder swipeHolder = holder;
        final SwipeItem swipeItem = (SwipeItem) swipeHolder.itemView;
        SwipeConfiguration configuration = onCreateSwipeConfiguration(position);
        swipeItem.setSwipeConfiguration(configuration);
        swipeItem.setSwipeListener(new SwipeItem.SwipeListener() {
            @Override
            public void onSwipeLeft() {
                onSwipe(swipeHolder.getPosition(), SWIPE_LEFT);
            }

            @Override
            public void onSwipeRight() {
                onSwipe(swipeHolder.getPosition(), SWIPE_RIGHT);
            }
        });
        onBindSwipeViewHolder(holder, position);
    }

    public abstract RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, final int position);

    @Override
    public abstract int getItemCount();

    public abstract SwipeConfiguration onCreateSwipeConfiguration(int position);

    public abstract void onSwipe(int position, int direction);
}
