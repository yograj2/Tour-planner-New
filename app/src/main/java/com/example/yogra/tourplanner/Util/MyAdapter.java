package com.example.yogra.tourplanner.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.yogra.tourplanner.Home.NewHomeActivity;
import com.bumptech.glide.Glide;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.SiteInfo;
import com.example.yogra.tourplanner.TourPlannerConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Place> tourdetails;
    private int mNoOfNights = 5;

    private static final String TAG = "MyAdapter";
    public Intent intent;

    public MyAdapter(Context context, List<Place> tourdetails) {
        this.context = context;
        this.tourdetails = tourdetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("MyAdapter", "inseide adapter");

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
        final Place place = tourdetails.get(position);
        myViewHolder.tourPlace.setText(place.getTourPlace());
        myViewHolder.tourDescription.setText(place.getTourDescription());
        myViewHolder.tourSightseeing.setText(place.getSightSeeing());
/*
        final int randomNum = ThreadLocalRandom.current().nextInt(4, 9 + 1);
        Log.d(TAG, "Random Number : " + randomNum);
        myViewHolder.numberOfNights.setText(String.valueOf(randomNum)+"N /"+(randomNum+1)+"D");
        String n=String.valueOf(place.getNightCharge()*randomNum);
        //myViewHolder.cost.setText(String.valueOf(place.getNightCharge()*randomNum));
        myViewHolder.cost.setText(n);*/
        myViewHolder.numberOfNights.setText(mNoOfNights + " N /" + (mNoOfNights + 1) + " D");
        String totalPrice = String.valueOf(place.getNightCharge() * mNoOfNights);
        myViewHolder.cost.setText(totalPrice);

        //myViewHolder.cost.setText(n);
        Log.d(TAG, "onBindViewHolder() -> image Data : " + place.getImageData());
        byte[] imageByteArray = Base64.decode(place.getImageData(), Base64.DEFAULT);
        Glide.with(context)
                .asBitmap()
                .load(imageByteArray)
                .into(myViewHolder.mImagedata);


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int clickedPosition=myViewHolder.getAdapterPosition();
                Place clickedUser=tourdetails.get(clickedPosition);
*/
                intent = new Intent(context, SiteInfo.class);
                intent.putExtra(TourPlannerConstant.TOUR_PLACE_ID, place.getId());
                /*intent.putExtra(TourPlannerConstant.TOUR_PLACE_NAME,clickedUser.getTourPlace());
                intent.putExtra(TourPlannerConstant.TOUR_PLACE_DESCRIPTION,clickedUser.getTourDescription());;
                intent.putExtra(TourPlannerConstant.TOUR_PLACE_SIGHTSEEING,clickedUser.getSightSeeing());
              intent.putExtra(TourPlannerConstant.TOUR_PLACE_IMAGE,clickedUser.getImageData());*/
                intent.putExtra("cost", place.getNightCharge() * mNoOfNights);
                // context.startActivity(intent);
                context.startActivity(intent);
            }
        });


        //myViewHolder.mImagedata.
        // myViewHolder.mImagedata.setImageResource(tourdetails.get(position).getImageData());
        //  viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());


        /*myViewHolder.mImagedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewHomeActivity.class);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return tourdetails.size();
    }

    public void setPlaceCollection(List<Place> collection) {
        this.tourdetails = collection;
        notifyDataSetChanged();
    }

    public void clearCollection() {
        if (tourdetails != null) {
            tourdetails.clear();
            notifyDataSetChanged();
        }
    }

    public void updateNumberOfNights(int numberOfNights) {
        mNoOfNights = numberOfNights;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tourPlace, tourDescription, tourSightseeing, cost, numberOfNights;
        private CardView cardView;
        ImageView mImagedata;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            tourPlace = itemView.findViewById(R.id.tv_place_recycler_view);
            tourDescription = itemView.findViewById(R.id.tv_description_recycler_view);
            tourSightseeing = itemView.findViewById(R.id.tv_sightseeing_recycler_view);
            mImagedata = itemView.findViewById(R.id.iv_recycler_view);
            cardView = itemView.findViewById(R.id.cardview);
            cost = itemView.findViewById(R.id.tv_day_night_charges);
            numberOfNights = itemView.findViewById(R.id.tv_price_label);

        }
    }
}
