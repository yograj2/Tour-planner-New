package com.example.yogra.tourplanner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yogra.tourplanner.Util.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SiteInfo extends AppCompatActivity {

    Intent intent;
   // public CardView cardView;
    public ImageView imageView;
    public TextView places;
    public TextView description;
    public TextView sightSeeing;
    public Context context;
    public Button button;
    public DatabaseReference databaseReference;
    private static final String TAG = "SiteInfo";
    public TextView price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_info);

       // cardView = findViewById(R.id.rec_cardview);
        imageView = findViewById(R.id.iv_image);
        context = this;

        places = findViewById(R.id.tv_place_recycler_view_main);
        description = findViewById(R.id.tv_description_recycler_view_main);
        sightSeeing=findViewById(R.id.tv_sightseeing_recycler_view_main);
        price = findViewById(R.id.tv_day_night);
       // button = findViewById(R.id.siteinfo_btn);

        intent = getIntent();
        String data = intent.getStringExtra(TourPlannerConstant.TOUR_PLACE_ID);
        Log.d(TAG,"Successfully apply "+ data);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TourPlace").child(data);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Place place = dataSnapshot.getValue(Place.class);
                   /* String data = place.getTourPlace();
                    place.setTourPlace(data);*/

                   places.setText(place.getTourPlace());
                   description.setText(place.getTourDescription());
                   sightSeeing.setText(place.getSightSeeing());

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

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
