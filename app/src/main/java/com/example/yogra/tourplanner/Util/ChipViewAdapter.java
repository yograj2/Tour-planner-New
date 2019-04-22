package com.example.yogra.tourplanner.Util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yogra.tourplanner.R;

import java.util.List;

public class ChipViewAdapter extends RecyclerView.Adapter<ChipViewAdapter.MyViewHolderOne> {

    private Context context;
    private List<String> mSightSeeingList;

    public ChipViewAdapter(Context context,List<String> sightSeeingList) {
        this.context = context;
        this.mSightSeeingList = sightSeeingList;
    }


    @NonNull
    @Override
    public MyViewHolderOne onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolderOne(LayoutInflater.from(context).inflate(R.layout.sightseeingchip,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderOne myViewHolderOne, int position) {

        myViewHolderOne.chip.setText(mSightSeeingList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSightSeeingList.size();
    }

    public void setCollection(List<String> collection) {
        mSightSeeingList = collection;
        notifyDataSetChanged();
    }

    class MyViewHolderOne extends RecyclerView.ViewHolder{

        public Chip chip;

        public MyViewHolderOne(@NonNull View itemView) {
            super(itemView);

            chip = itemView.findViewById(R.id.cv_seightseeing);
        }
    }
}
