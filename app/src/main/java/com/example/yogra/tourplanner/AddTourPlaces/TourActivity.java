package com.example.yogra.tourplanner.AddTourPlaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yogra.tourplanner.R;

public class TourActivity extends AppCompatActivity {

    public TextView tourplace;
    public TextView tourdescription;
    public TextView seightseing;
    public Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        tourplace = findViewById(R.id.tv_tour_place_name);
        tourdescription = findViewById(R.id.tv_tour_desc_info);
        seightseing = findViewById(R.id.tv_tour_sight_info);
        buttonAdd = findViewById(R.id.btn_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
