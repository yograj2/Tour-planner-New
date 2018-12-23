package com.example.yogra.tourplanner.Home;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yogra.tourplanner.AddTourPlaces.TourActivity;
import com.example.yogra.tourplanner.R;

public class HomeActivity extends AppCompatActivity {

    public FloatingActionButton floatingActionButtonAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButtonAdmin = findViewById(R.id.Home_button_admin);

        floatingActionButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,TourActivity.class);
                startActivity(intent);
            }
        });
    }
}
