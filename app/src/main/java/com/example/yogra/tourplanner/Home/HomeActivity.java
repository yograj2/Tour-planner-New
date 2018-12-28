package com.example.yogra.tourplanner.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.yogra.tourplanner.AddTourPlaces.TourActivity;
import com.example.yogra.tourplanner.Util.MyAdapter;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public FloatingActionButton floatingActionButtonAdmin;
    public DatabaseReference databaseReference;
    public RecyclerView recyclerView;
    ArrayList<Place> tourdetails;
    private MyAdapter adapter;

   Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourdetails = new ArrayList<Place>();

        floatingActionButtonAdmin = findViewById(R.id.Home_button_admin);
         intent=getIntent();
        String normalUser=intent.getStringExtra("normalUser");
        //String adminUser=intent.getStringExtra("admin");

        if(normalUser.equals("Success"))
        {
            floatingActionButtonAdmin.show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TourPlace");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Place p = ds.getValue(Place.class);
                    tourdetails.add(p);
                }
                 adapter = new MyAdapter(HomeActivity.this,tourdetails);
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });

        /*final String email = "admin@gmail.com";
        final String password = "admin@1234";

        DatabaseReference users = databaseReference.child("users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean isExit = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if (ds.child("email").getValue().toString().equals(email)
                            && ds.child("password").getValue().toString().equals(password)){
                        isExit = true;
                    }
                }
                if (isExit){
                    floatingActionButtonAdmin.show();
                }
                else {
                    floatingActionButtonAdmin.hide();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("HomeActivity","Canelled");

            }
        });*/

        floatingActionButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(HomeActivity.this,TourActivity.class);
                startActivity(intent);
            }
        });
    }
}
