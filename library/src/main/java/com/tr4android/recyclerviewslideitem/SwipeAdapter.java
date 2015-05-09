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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        SwipeConfiguration configuration = onCreateSwipeConfiguration(swipeItem.getContext(), position);
        swipeItem.setSwipeConfiguration(configuration);
        swipeItem.setSwipeListener(new SwipeItem.OnSwipeListener() {
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

    public abstract SwipeConfiguration onCreateSwipeConfiguration(Context context, int position);

    public abstract void onSwipe(int position, int direction);
}
