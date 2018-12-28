package com.example.yogra.tourplanner.AddTourPlaces;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TourActivity extends AppCompatActivity {

    public EditText tourplace;
    public EditText tourdescription;
    public EditText seightseing;
    public Button buttonAdd;
    public DatabaseReference databaseReference;
    private static final String TAG = "TourActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        tourplace = findViewById(R.id.et_tour_placename);
        tourdescription = findViewById(R.id.et_tour_description);
        seightseing = findViewById(R.id.et_tour_sightseeing_info);
        buttonAdd = findViewById(R.id.btn_add);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tourPlace = tourplace.getText().toString();
                String tourDescription=tourdescription.getText().toString();
                String sightSeeing=seightseing.getText().toString();

               /* if (tourPlace.isEmpty()){
                    Toast.makeText(TourActivity.this, "not be empty", Toast.LENGTH_SHORT).show();
                }*/

               // Place place = new Place(tourPlace,tourDescription,sightSeeing);
                Place place = new Place();
                place.setTourPlace(tourplace.getText().toString());
                place.setTourDescription(tourdescription.getText().toString());
                place.setSightSeeing(seightseing.getText().toString());

                databaseReference.child("TourPlace").setValue(place)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG,"Sucssefully add in database");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"Failed to add in database");
                            }
                        });


            }
        });

    }
}
