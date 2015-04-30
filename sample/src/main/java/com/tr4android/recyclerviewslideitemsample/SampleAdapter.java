package com.tr4android.recyclerviewslideitemsample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ThomasR on 28.04.2015.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder>{

    public class SampleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SampleViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sample, parent, false);
        SampleViewHolder sampleViewHolder = new SampleViewHolder(v);
        return sampleViewHolder;
    }

    @Override
    public void onBindViewHolder(SampleViewHolder sampleViewHolder, int i) {
        sampleViewHolder.textView.setText("person" + String.valueOf(i) + "@sample.com");
    }

    @Override
    public int getItemCount() {
        return 25;
    }
}
