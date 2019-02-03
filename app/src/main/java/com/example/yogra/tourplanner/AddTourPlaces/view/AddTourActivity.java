package com.example.yogra.tourplanner.AddTourPlaces.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.SQLiteDatabase.view.DatabaseHelper;
import com.example.yogra.tourplanner.Util.ImageUtil;
import com.example.yogra.tourplanner.Util.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AddTourActivity extends Activity {

    public EditText tourplace;
    public EditText tourdescription;
    public EditText tourseightseing;
    public EditText dayNight;
    public Button buttonAdd;
    public DatabaseReference databaseReference;
    public FloatingActionButton mFloatingActionButton;
    private ImageView mIvPlaceImage;

    private String mImageData;

    private int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "AddTourActivity";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtour);

        tourplace = findViewById(R.id.et_tour_place_info);
        tourdescription = findViewById(R.id.et_tour_description_info);
        tourseightseing = findViewById(R.id.et_tour_seightseeing_info);
        dayNight = findViewById(R.id.et_tour_dayNight_info);
        buttonAdd = findViewById(R.id.btn_add);
        mFloatingActionButton = findViewById(R.id.add_tour_floatingButton);
        mIvPlaceImage = findViewById(R.id.iv_tour_image);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        context = this;

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tourPlace = tourplace.getText().toString();
                String tourDescription=tourdescription.getText().toString();
                String tourSeightseeing=tourseightseing.getText().toString();
                String tourDaynight = dayNight.getText().toString();
                String imageData = mImageData.getBytes().toString();

                if (tourPlace.isEmpty()){
                    Toast.makeText(AddTourActivity.this, "place should not be empty", Toast.LENGTH_SHORT).show();
                }
               else if (tourDescription.isEmpty()){
                    Toast.makeText(AddTourActivity.this, "description should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if (tourSeightseeing.isEmpty()){
                    Toast.makeText(AddTourActivity.this, "seightseeing should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if (tourDaynight.isEmpty()){
                    Toast.makeText(AddTourActivity.this, "dayNight should not be empty", Toast.LENGTH_SHORT).show();

                }
                else if (imageData.isEmpty()){
                    Toast.makeText(AddTourActivity.this, "image should not be empty", Toast.LENGTH_SHORT).show();
                }
                else {


                    // Place place = new Place(tourPlace,tourDescription,sightSeeing);
                    Place place = new Place();
                    place.setTourPlace(tourplace.getText().toString());
                    place.setTourDescription(tourdescription.getText().toString());
                    place.setSightSeeing(tourseightseing.getText().toString());
                    place.setNightCharge(Integer.parseInt(dayNight.getText().toString()));

                    place.setImageData(mImageData);

                    //String placeName = tourplace.getText().toString();

                    databaseReference.child("TourPlace").child(String.valueOf(System.currentTimeMillis())).setValue(place)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Sucssefully add in database");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Failed to add in database");
                                }
                            });

                    /*DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    Place place1 = new Place();
                    place1.setTourPlace(tourplace.getText().toString());
                    place1.setTourDescription(tourdescription.getText().toString());
                    place1.setSightSeeing(tourseightseing.getText().toString());
                    place1.setNightCharge(Integer.parseInt(dayNight.getText().toString()));
                    databaseHelper.insertPlace(place1);*/

                }

/*
                DatabaseHelper databaseHelper1 = new DatabaseHelper(context);
*/


            }
        });



        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/


                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE_REQUEST);
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            //mIvPlaceImage.setImageBitmap(bitmap);

            //Get Base 64 data from Bitmap which is required to upload in firebase
            /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            mImageData = Base64.encodeToString(byteArray, Base64.DEFAULT);*/

            mImageData = ImageUtil.convert(bitmap);
            Bitmap imageBitmap = ImageUtil.convert(mImageData);
            mIvPlaceImage.setImageBitmap(imageBitmap);

            Log.d(TAG, "Base 64 : " + mImageData);
        }
    }
}
