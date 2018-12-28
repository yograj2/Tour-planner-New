package com.example.yogra.tourplanner.Util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogra.tourplanner.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Place> tourdetails;

    public MyAdapter(Context context, ArrayList<Place> tourdetails) {
        this.context = context;
        this.tourdetails = tourdetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        Log.d("MyAdapter","inseide adapter");

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tourPlace.setText(tourdetails.get(position).getTourPlace());
        myViewHolder.tourDescription.setText(tourdetails.get(position).getTourDescription());
        myViewHolder.tourSightseeing.setText(tourdetails.get(position).getSightSeeing());

    }

    @Override
    public int getItemCount() {
        return tourdetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tourPlace,tourDescription,tourSightseeing;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tourPlace = itemView.findViewById(R.id.rec_place);
            tourDescription = itemView.findViewById(R.id.rec_description);
            tourSightseeing=itemView.findViewById(R.id.rec_sightseeing);

        }
    }
}
