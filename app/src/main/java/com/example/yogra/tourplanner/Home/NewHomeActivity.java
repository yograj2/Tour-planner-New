package com.example.yogra.tourplanner.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.MyAdapterMain;
import com.example.yogra.tourplanner.Util.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewHomeActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ArrayList<Place> tourDetails;
    public DatabaseReference databaseReference;
    public MyAdapterMain myAdapterMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);


        // Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        recyclerView = findViewById(R.id.rec_new_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourDetails = new ArrayList<Place>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TourPlace");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Place p = ds.getValue(Place.class);
                    tourDetails.add(p);
                }
                myAdapterMain = new MyAdapterMain(NewHomeActivity.this,tourDetails);
                recyclerView.setAdapter(myAdapterMain);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NewHomeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        });
    }

    //Back Button

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
