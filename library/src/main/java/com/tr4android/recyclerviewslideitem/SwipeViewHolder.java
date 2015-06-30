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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SwipeViewHolder extends RecyclerView.ViewHolder {
    //private static final String LOG_TAG = "SwipeViewHolder";

    // index of custom view that slides
    private static final int INDEX_SWIPE_ITEM = 2;

    // index of included background view for slide hint
    private static final int INDEX_LEAVE_BEHIND = 0;

    // index of included background view for slide undo
    private static final int INDEX_FOLLOW_UP_ACTION = 1;

    // configuration for swipe
    private SwipeConfiguration mConfiguration;

    protected enum SwipeState {
        STATE_SWIPE_LEFT, STATE_SWIPE_RIGHT, STATE_ACTION_LEFT, STATE_ACTION_RIGHT
    }

    private SwipeState mState;

    public SwipeViewHolder(View itemView) {
        super(itemView);
    }

    public void setSwipeConfiguration(SwipeConfiguration configuration) {
        mConfiguration = configuration;
    }

    public SwipeConfiguration getSwipeConfiguration() {
        return mConfiguration;
    }

    public View getSwipeItem() {
        return ((ViewGroup) itemView).getChildAt(INDEX_SWIPE_ITEM);
    }

    public void setState(SwipeState state) {
        if (mState != state) {
            mState = state;
            switch (mState) {
                case STATE_SWIPE_LEFT:
                    itemView.setBackgroundColor(mConfiguration.getLeftBackgroundColor());
                    ((ImageView) itemView.findViewById(R.id.imageViewLeft)).setImageResource(0);
                    ((ImageView) itemView.findViewById(R.id.imageViewRight)).setImageResource(mConfiguration.getLeftDrawableResource());
                    showLeaveBehind();
                    break;
                case STATE_SWIPE_RIGHT:
                    itemView.setBackgroundColor(mConfiguration.getRightBackgroundColor());
                    ((ImageView) itemView.findViewById(R.id.imageViewLeft)).setImageResource(mConfiguration.getRightDrawableResource());
                    ((ImageView) itemView.findViewById(R.id.imageViewRight)).setImageResource(0);
                    showLeaveBehind();
                    break;
                case STATE_ACTION_LEFT:
                    ((TextView) itemView.findViewById(R.id.undoDescription)).setText(mConfiguration.getLeftUndoDescription());
                    int color = mConfiguration.getLeftDescriptionTextColor();
                    ((TextView) itemView.findViewById(R.id.textViewDescription)).setTextColor(color);
                    ((TextView) itemView.findViewById(R.id.undoDescription)).setTextColor(color);
                    ((TextView) itemView.findViewById(R.id.undoButton)).setTextColor(color);
                    //itemView.findViewById(R.id.undoButton).setOnClickListener(leftUndoClickListener);
                    showFollowUpAction();
                    break;
                case STATE_ACTION_RIGHT:
                    ((TextView) itemView.findViewById(R.id.undoDescription)).setText(mConfiguration.getRightUndoDescription());
                    color = mConfiguration.getRightDescriptionTextColor();
                    ((TextView) itemView.findViewById(R.id.textViewDescription)).setTextColor(color);
                    ((TextView) itemView.findViewById(R.id.undoDescription)).setTextColor(color);
                    ((TextView) itemView.findViewById(R.id.undoButton)).setTextColor(color);
                    //itemView.findViewById(R.id.undoButton).setOnClickListener(rightUndoClickListener);
                    showFollowUpAction();
                    break;
            }
        }
    }

    private void showFollowUpAction() {
        ViewGroup vg = (ViewGroup) itemView;
        vg.getChildAt(INDEX_LEAVE_BEHIND).setVisibility(View.GONE);
        vg.getChildAt(INDEX_FOLLOW_UP_ACTION).setVisibility(View.VISIBLE);
    }

    private void showLeaveBehind() {
        ViewGroup vg = (ViewGroup) itemView;
        vg.getChildAt(INDEX_FOLLOW_UP_ACTION).setVisibility(View.GONE);
        vg.getChildAt(INDEX_LEAVE_BEHIND).setVisibility(View.VISIBLE);
    }
}
