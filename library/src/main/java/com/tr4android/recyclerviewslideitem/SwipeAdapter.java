package com.tr4android.recyclerviewslideitem;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by ThomasR on 03.05.2015.
 */
public abstract class SwipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RecyclerView mRecyclerView;

    public SwipeAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SwipeItem item = (SwipeItem) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swipe, parent, false);
        RecyclerView.ViewHolder holder = onCreateSwipeViewHolder(item, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final SwipeItem swipeItem = (SwipeItem) holder.itemView;
        SwipeConfiguration configuration = onCreateSwipeConfiguration(position);
        swipeItem.setSwipeConfiguration(configuration);
        swipeItem.setSwipeListener(new SwipeItem.SwipeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onSwipeLeft() {
                Log.i("Swipe", "Left");
                onSwipe(holder.getPosition());
            }

            @Override
            public void onSwipeRight() {
                Log.i("Swipe", "Right");
                onSwipe(holder.getPosition());
            }
        });
    }

    public abstract RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract int getItemCount();

    public abstract SwipeConfiguration onCreateSwipeConfiguration(int position);

    public abstract void onSwipe(int position);
}
