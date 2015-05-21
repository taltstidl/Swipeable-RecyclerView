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

package com.tr4android.recyclerviewslideitemsample;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.ArrayList;

public class SampleAdapter extends SwipeAdapter {
    ArrayList<String> mDataset;
    int[] colors = new int[]{R.color.color_red, R.color.color_pink, R.color.color_purple, R.color.color_deep_purple, R.color.color_indigo, R.color.color_blue, R.color.color_light_blue, R.color.color_cyan, R.color.color_teal, R.color.color_green, R.color.color_light_green, R.color.color_lime, R.color.color_yellow, R.color.color_amber, R.color.color_orange, R.color.color_deep_orange, R.color.color_brown, R.color.color_grey, R.color.color_blue_grey};

    private Context mContext;

    public SampleAdapter(Context context) {
        mContext = context;
        // create dummy dataset
        mDataset = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mDataset.add("person" + String.valueOf(i + 1) + "@sample.com");
        }
    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {
        View avatarView;
        TextView textView;

        public SampleViewHolder(View view) {
            super(view);
            avatarView = view.findViewById(R.id.avatarView);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sample, parent, true);
        return new SampleViewHolder(v);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder swipeViewHolder, int i) {
        SampleViewHolder sampleViewHolder = (SampleViewHolder) swipeViewHolder;
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(mContext.getResources().getColor(colors[((int) (Math.random() * (colors.length - 1)))]));
        sampleViewHolder.avatarView.setBackgroundDrawable(drawable);
        sampleViewHolder.textView.setText(mDataset.get(i));
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int position) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.color_delete)
                .setRightBackgroundColorResource(R.color.color_mark)
                .setDrawableResource(R.drawable.ic_delete_white_24dp)
                .setRightDrawableResource(R.drawable.ic_done_white_24dp)
                .setLeftUndoable(true)
                .setLeftUndoDescription(R.string.action_deleted)
                .setDescriptionTextColorResource(android.R.color.white)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.RESTRICTED_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(int position, int direction) {
        if (direction == SWIPE_LEFT) {
            mDataset.remove(position);
            notifyItemRemoved(position);
            Toast toast = Toast.makeText(mContext, "Deleted item at position " + position, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(mContext, "Marked item as read at position " + position, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}