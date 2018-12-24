package com.example.yogra.tourplanner.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yogra.tourplanner.AddTourPlaces.TourActivity;
import com.example.yogra.tourplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    public FloatingActionButton floatingActionButtonAdmin;
   // public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButtonAdmin = findViewById(R.id.Home_button_admin);
       // databaseReference = FirebaseDatabase.getInstance().getReference();

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
                Intent intent = new Intent(HomeActivity.this,TourActivity.class);
                startActivity(intent);
            }
        });
    }
}
