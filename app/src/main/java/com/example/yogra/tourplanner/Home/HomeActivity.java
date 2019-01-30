package com.example.yogra.tourplanner.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import com.example.yogra.tourplanner.AddTourPlaces.view.AddTourActivity;
import com.example.yogra.tourplanner.BaseActivity;
import com.example.yogra.tourplanner.BottomSheetDialog.BottomSheetDialog;
import com.example.yogra.tourplanner.BottomSheetDialog.BottomSheetFilter;
import com.example.yogra.tourplanner.Login.view.LoginActivity;
import com.example.yogra.tourplanner.TourPlannerConstant;
import com.example.yogra.tourplanner.Util.MyAdapter;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.Place;
import com.example.yogra.tourplanner.Util.PreferenceHelper;
import com.example.yogra.tourplanner.Util.SystemUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class HomeActivity extends BaseActivity {

    public FloatingActionButton floatingActionButtonAdmin;
    public DatabaseReference databaseReference;
    public RecyclerView recyclerView;
    private ArrayList<Place> tourdetails;
    private MyAdapter adapter;
    public Button logout;
    //PreferenceHelper mPreference;
    public boolean shared;
    public CardView cardView;
    Context context;
    Intent intent;
    private DrawerLayout drawerLayout;
    private static final String TAG = "HomeActivity";
    public TextView sort;
    public TextView filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        //drawer Layout

        /*drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();*/

        //Bottom Dialgog

        sort = findViewById(R.id.tv_sort);
        filter = findViewById(R.id.tv_filter);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourdetails = new ArrayList<Place>();
        context = this;



        floatingActionButtonAdmin = findViewById(R.id.Home_button_admin);
       // logout = findViewById(R.id.logout_button);
        cardView = findViewById(R.id.cardview);

        SystemUtil.isStoragePermissionGranted(this);

        intent = getIntent();
        //String normalUser=intent.getStringExtra("normalUser");
        //String adminUser=intent.getStringExtra("admin");

        //String userType = intent.getStringExtra(TourPlannerConstant.USER_TYPE);
        String userType=mPreferenceHelper.getString(TourPlannerConstant.USER_TYPE,"");
        if (TourPlannerConstant.NORMAL_USER.equalsIgnoreCase(userType))
        {
            floatingActionButtonAdmin.setVisibility(View.GONE);
        } else {
            floatingActionButtonAdmin.setVisibility(View.VISIBLE);
        }



        databaseReference = FirebaseDatabase.getInstance().getReference().child("TourPlace");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("TAG", "inside onDataChnaged " + ds.getKey());
                    Place p = ds.getValue(Place.class);
                    p.setId(ds.getKey());
                   /* Place place = new Place();
                    String name = p.getTourPlace();
                    place.setTourPlace(name);*/
                   // String demo = p.getTourPlace();
                    tourdetails.add(p);
                }
                adapter = new MyAdapter(HomeActivity.this, tourdetails);
                recyclerView.setAdapter(adapter);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                mPreferenceHelper.putBoolean(PreferenceHelper.IS_LOGIN,false);

            }
        });*/


        floatingActionButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(HomeActivity.this, AddTourActivity.class);
                startActivity(intent);
            }
        });

        //Bottom Sheet Dialog

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(),bottomSheetDialog.getTag());
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFilter bottomSheetFilter = new BottomSheetFilter();
                bottomSheetFilter.show(getSupportFragmentManager(),bottomSheetFilter.getTag());
            }
        });

    }

   /* @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void init() {

    }

}
