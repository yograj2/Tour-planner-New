package com.example.yogra.tourplanner.Home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
//import android.widget.Toolbar;
import com.example.yogra.tourplanner.AddTourPlaces.view.AddTourActivity;
import com.example.yogra.tourplanner.BaseActivity;
import com.example.yogra.tourplanner.BottomSheetDialog.BottomSheetSort;
//import com.example.yogra.tourplanner.BottomSheetDialog.BottomSheetFilter;
import com.example.yogra.tourplanner.Login.view.LoginActivity;
import com.example.yogra.tourplanner.SQLiteDatabase.view.DatabaseHelper;
import com.example.yogra.tourplanner.TourPlannerConstant;
import com.example.yogra.tourplanner.Util.MyAdapter;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.Place;
import com.example.yogra.tourplanner.Util.SystemUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class HomeActivity extends BaseActivity {

    public FloatingActionButton floatingActionButtonAdmin;
    public DatabaseReference databaseReference;
    public RecyclerView recyclerView;
    private List<Place> tourdetails;
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
    public DatabaseHelper databaseHelper;
    private android.support.v7.widget.Toolbar toolbar;
    public String currentSortingOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* toolbar =findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);



        //drawer Layout

        drawerLayout = findViewById(R.id.drawer_layout);

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
        String userType = mPreferenceHelper.getString(TourPlannerConstant.USER_TYPE, "");
        if (TourPlannerConstant.NORMAL_USER.equalsIgnoreCase(userType)) {
            floatingActionButtonAdmin.setVisibility(View.GONE);
        } else {
            floatingActionButtonAdmin.setVisibility(View.VISIBLE);
        }

        adapter = new MyAdapter(HomeActivity.this, tourdetails);

        recyclerView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("TourPlace");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clearCollection();
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
                adapter.setPlaceCollection(tourdetails);
                //DATABASE HELPER

                databaseHelper = new DatabaseHelper(context);
                databaseHelper.insertPlaceList(tourdetails);
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
                /*BottomSheetSort bottomSheetDialog = new BottomSheetSort();
                bottomSheetDialog.show(getSupportFragmentManager(), bottomSheetDialog.getTag());*/
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null, false);
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(view);
                dialog.show();
                RadioGroup group = view.findViewById(R.id.radio_group_sort);
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.rb_low_to_high)
                        {
                            adapter.clearCollection();
                            //Need to call database query
                            // order by Night cost ascending
                            currentSortingOrder=TourPlannerConstant.ORDER_ASC;
                            tourdetails = databaseHelper.sort(currentSortingOrder);
                            adapter.setPlaceCollection(tourdetails);
                            Log.d(TAG,"data in ascending");
                        }
                        else
                            {
                                adapter.clearCollection();
                                currentSortingOrder=TourPlannerConstant.ORDER_DESC;
                                tourdetails = databaseHelper.sort(currentSortingOrder);
                                adapter.setPlaceCollection(tourdetails);
                                Log.d(TAG,"data in descending");

               /*             databaseHelper.desc(tourdetails);
                                adapter.setPlaceCollection(tourdetails);*/
                        }
                    }
                });
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BottomSheetFilter bottomSheetFilter = new BottomSheetFilter();
                bottomSheetFilter.show(getSupportFragmentManager(), bottomSheetFilter.getTag());*/
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet_filter,null ,false);
                BottomSheetDialog dialogFilter = new BottomSheetDialog(context);
                dialogFilter.setContentView(view);
                dialogFilter.show();
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_click);
                final CheckBox checkBoxBudget = (CheckBox) view.findViewById(R.id.checkbox_click_duration);
                final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.rd_budget_container);
                final LinearLayout linearLayoutBudget = (LinearLayout) view.findViewById(R.id.rd_duration_container);

                final RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.rd_one);
                final RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.rd_two);
                final RadioButton radioButton3 = (RadioButton) view.findViewById(R.id.rd_three);
                final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_budget);

                linearLayout.setVisibility(View.GONE);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            linearLayout.setVisibility(View.VISIBLE);

                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId)
                                {
                                    if (checkedId == R.id.rd_one)
                                    {
                                        adapter.clearCollection();
                                        tourdetails=databaseHelper.applyFilter(currentSortingOrder,2000);
                                        adapter.setPlaceCollection(tourdetails);
                                    }

                                    else if (checkedId==R.id.rd_two)
                                    {
                                        adapter.clearCollection();
                                        tourdetails=databaseHelper.applyFilter(currentSortingOrder,3000);
                                        adapter.setPlaceCollection(tourdetails);
                                    }

                                    else
                                    {
                                        adapter.clearCollection();
                                        tourdetails=databaseHelper.applyFilter(currentSortingOrder,4000);
                                        adapter.setPlaceCollection(tourdetails);
                                    }
                                }
                            });
                        }
                        else {
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                });



                linearLayoutBudget.setVisibility(View.GONE);

                checkBoxBudget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            linearLayoutBudget.setVisibility(View.VISIBLE);
                        }
                        else {
                            linearLayoutBudget.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }

    //Drawer Layout

   /* @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    //Overflow menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void init() {

    }

}
