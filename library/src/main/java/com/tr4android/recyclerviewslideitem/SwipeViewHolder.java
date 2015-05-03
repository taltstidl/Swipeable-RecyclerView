package com.tr4android.recyclerviewslideitem;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ThomasR on 03.05.2015.
 */
public class SwipeViewHolder extends RecyclerView.ViewHolder {
    SwipeItem swipeItem;

    public SwipeViewHolder(View itemView) {
        super(itemView);
        swipeItem = (SwipeItem) itemView;
    }
}
