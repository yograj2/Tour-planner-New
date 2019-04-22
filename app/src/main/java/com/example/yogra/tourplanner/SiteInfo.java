package com.example.yogra.tourplanner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yogra.tourplanner.Util.Calculation;
import com.example.yogra.tourplanner.Util.ChipViewAdapter;
import com.example.yogra.tourplanner.Util.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SiteInfo extends AppCompatActivity {

    Intent intent;
    // public CardView cardView;
    public ImageView imageView;
    public TextView places;
    public TextView description;
    public TextView sightSeeing;
    public Context context;
    public TextView buttonPrice;
    public DatabaseReference databaseReference;
    private static final String TAG = "SiteInfo";
    public TextView price;
    public TextView total_cost;

    public RecyclerView recyclerView;
    public List<String> tourSeightseeing;
    public ChipViewAdapter chipViewAdapter;
    public ChipGroup chipGroup;

    public RatingBar ratingBar;
    public TextView submitRatingBar;
    public boolean ratigChecked;
    public int ratingId = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_info);

        ratingBar = findViewById(R.id.rating_bar);
        submitRatingBar = findViewById(R.id.rate_submit);

        // cardView = findViewById(R.id.rec_cardview);
        imageView = findViewById(R.id.iv_image);
        context = this;

        //Back Button

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        places = findViewById(R.id.tv_place_recycler_view_main);
        description = findViewById(R.id.tv_description_recycler_view_main);

        // sightSeeing=findViewById(R.id.tv_sightseeing_recycler_view_main);

        chipGroup = findViewById(R.id.sight_seeing_chip_group);

        recyclerView = findViewById(R.id.rv_siteSeeing);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourSeightseeing = new ArrayList<>();

        chipViewAdapter = new ChipViewAdapter(context, tourSeightseeing);
        recyclerView.setAdapter(chipViewAdapter);

        //price = findViewById(R.id.tv_day_night);
        // button = findViewById(R.id.siteinfo_btn);
        //total_cost = findViewById(R.id.tv_day_night);
        buttonPrice = findViewById(R.id.calculate);

        intent = getIntent();
        String data = intent.getStringExtra(TourPlannerConstant.TOUR_PLACE_ID);
        Log.d(TAG,"Successfully apply "+ data);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TourPlace").child(data);
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Place place = dataSnapshot.getValue(Place.class);
                   /* String data = place.getTourPlace();
                    place.setTourPlace(data);*/

                places.setText(place.getTourPlace());
                description.setText(place.getTourDescription());
                //sightSeeing.setText(place.getSightSeeing());
                String sightSeeingSeparatedByComma = place.getSightSeeing();

                List<String> sightSeeingList = Arrays.asList(sightSeeingSeparatedByComma.split(","));
                chipViewAdapter.setCollection(sightSeeingList);

                chipGroup.removeAllViews();
                for (String sight : sightSeeingList) {
                    Chip chip = new Chip(context);
                    chip.setText(sight);
                    chipGroup.addView(chip);
                }

                String data = place.getImageData();
                byte[] image = Base64.decode(data, Base64.DEFAULT);
                Glide.with(context)
                        .asBitmap()
                        .load(image)
                        .into(imageView);

                //Log.d(TAG, "onDataChange: " +data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        total_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*intent = new Intent(SiteInfo.this,Calculation.class);
                startActivity(intent);*//*
            }
        });*/

        Bundle bundle = getIntent().getExtras();
        int totalCost=bundle.getInt("cost");
        Log.d(TAG,"Price on button"+totalCost);
        buttonPrice.setText("Price : "+String.valueOf(totalCost));

        if (ratingId!= -1){
            ratigChecked = true;
        }

        submitRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ratingBar!= null){
                    ratigChecked =true;
                    String totalStars = "Total Stars :" +ratingBar.getNumStars();
                    String rating = "Rating :" +ratingBar.getRating();
                    Toast.makeText(SiteInfo.this,totalStars + "\n" + rating,Toast.LENGTH_SHORT).show();
                }
                else{
                    ratigChecked = false;
                    ratingId = -1;
                }

                /*String totalStars = "Total Stars :" +ratingBar.getNumStars();
                String rating = "Rating :" +ratingBar.getRating();
                Toast.makeText(SiteInfo.this,totalStars + "\n" + rating,Toast.LENGTH_SHORT).show();*/

            }
        });



        // place.setText(data);



        /*String placeData=intent.getStringExtra(TourPlannerConstant.TOUR_PLACE_NAME);
        String descriptionData=intent.getStringExtra(TourPlannerConstant.TOUR_PLACE_DESCRIPTION);
        String sightSeeingData=intent.getStringExtra(TourPlannerConstant.TOUR_PLACE_SIGHTSEEING);

        String imageByteArray=intent.getStringExtra(TourPlannerConstant.TOUR_PLACE_IMAGE);

        byte[] image = Base64.decode(imageByteArray, Base64.DEFAULT);
        Glide.with(context)
                .asBitmap()
                .load(image)
                .into(imageView);

        place.setText(placeData);
        description.setText(descriptionData);
        sightSeeing.setText(sightSeeingData);*/

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Book suceesfully", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    //Back Button Method
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

