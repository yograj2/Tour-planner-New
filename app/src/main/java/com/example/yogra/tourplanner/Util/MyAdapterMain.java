package com.example.yogra.tourplanner.Util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogra.tourplanner.R;

import java.util.ArrayList;

public class MyAdapterMain extends RecyclerView.Adapter<MyAdapterMain.MyViewHolder> {

    public Context context;
    private ArrayList<Place> tourDetails;

    public MyAdapterMain(Context context, ArrayList<Place> tourDetails) {
        this.context = context;
        this.tourDetails = tourDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_main,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tourplace.setText(tourDetails.get(position).getTourPlace());
        myViewHolder.tourdescription.setText(tourDetails.get(position).getTourDescription());
        myViewHolder.tourseightseeing.setText(tourDetails.get(position).getSightSeeing());

    }

    @Override
    public int getItemCount() {
        return tourDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tourplace,tourdescription,tourseightseeing;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tourplace = itemView.findViewById(R.id.tv_place_recycler_view_main);
            tourdescription =  itemView.findViewById(R.id.tv_description_recycler_view_main);
           // tourseightseeing = itemView.findViewById(R.id.tv_sightseeing_recycler_view_main);
        }
    }
}
