package com.tr4android.recyclerviewslideitem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ThomasR on 03.05.2015.
 */
public class SwipeAdapter extends RecyclerView.Adapter<SwipeViewHolder> {

    @Override
    public SwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SwipeItem item = (SwipeItem) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swipe, parent, false);
        SwipeViewHolder holder = onCreateSwipeViewHolder(item, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(SwipeViewHolder holder, int position) {

    }

    public SwipeViewHolder onCreateSwipeViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void onCreateSwipe(int position) {

    }
}
