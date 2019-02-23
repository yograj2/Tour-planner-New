package com.example.yogra.tourplanner.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
//import android.widget.Toolbar;
import com.example.yogra.tourplanner.AddTourPlaces.view.AddTourActivity;
import com.example.yogra.tourplanner.BaseActivity;
//import com.example.yogra.tourplanner.BottomSheetDialog.BottomSheetSort;
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
    String budgetOne;
    String budgetTwo;
    String budgetThree;
    public boolean mBudgetFilterSelected;
    public boolean mBudgetSortSelected;
    public boolean mDurationSelected;
    public int mSelectedBudgetId = -1;
    public int mSelectedSortId = -1;
    public int mSelectedDurationId = -1;
    public int mSelectedBudget = 0;

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

        /*budgetOne=TourPlannerConstant.BUDGET_FILTER_ONE;
        budgetTwo=TourPlannerConstant.BUDGET_FILTER_TWO;
        budgetThree=TourPlannerConstant.BUDGET_FILTER_THREE;*/

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

                RadioGroup radioGroupSort = view.findViewById(R.id.radio_group_sort);

                if (mSelectedSortId != -1){
                    radioGroupSort.check(mSelectedSortId);
                }

                radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        mBudgetSortSelected = true;
                        mSelectedSortId = checkedId;
                        if (checkedId == R.id.rb_low_to_high) {
                            adapter.clearCollection();
                            //Need to call database query
                            // order by Night cost ascending
                            currentSortingOrder = TourPlannerConstant.ORDER_ASC;
                            tourdetails = databaseHelper.sort(currentSortingOrder,mSelectedBudget);
                            adapter.setPlaceCollection(tourdetails);
                            Log.d(TAG, "data in ascending");
                        }
                        else {

                            adapter.clearCollection();
                            currentSortingOrder = TourPlannerConstant.ORDER_DESC;
                            tourdetails = databaseHelper.sort(currentSortingOrder,mSelectedBudget);
                            adapter.setPlaceCollection(tourdetails);
                            Log.d(TAG, "data in descending");
                            mBudgetSortSelected = false;
                            mSelectedSortId = -1;

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
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null, false);
                BottomSheetDialog dialogFilter = new BottomSheetDialog(context);
                dialogFilter.setContentView(view);
                dialogFilter.show();
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox_click);
                final CheckBox checkBoxDuration = (CheckBox) view.findViewById(R.id.checkbox_click_duration);
                final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.rd_budget_container);
                final LinearLayout linearLayoutBudget = (LinearLayout) view.findViewById(R.id.rd_duration_container);

                final RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.rd_budget_2000);
                final RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.rd_budget_3000);
                final RadioButton radioButton3 = (RadioButton) view.findViewById(R.id.rd_budget_4000);
                final RadioGroup radioGroupBudget = (RadioGroup) view.findViewById(R.id.rg_budget);
                final RadioGroup radioGroupDuration = (RadioGroup) view.findViewById(R.id.rg_duration);

                radioGroupBudget.setVisibility(View.GONE);
                checkBox.setChecked(mBudgetFilterSelected);
                if (mBudgetFilterSelected) {
                    radioGroupBudget.setVisibility(View.VISIBLE);
                }
                if (mSelectedBudgetId != -1) {
                    radioGroupBudget.check(mSelectedBudgetId);
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            radioGroupBudget.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout.setVisibility(View.GONE);
                            mBudgetFilterSelected = false;
                            mSelectedBudgetId = -1;
                            radioGroupBudget.clearCheck();
                        }
                    }
                });


                radioGroupBudget.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        mBudgetFilterSelected = true;
                        mSelectedBudgetId = checkedId;
                        if (checkedId == R.id.rd_budget_2000) {
                            mSelectedBudget = TourPlannerConstant.BUDGET_FILTER_ONE;
                        } else if (checkedId == R.id.rd_budget_3000) {
                            mSelectedBudget = TourPlannerConstant.BUDGET_FILTER_TWO;
                        } else {
                            mSelectedBudget = TourPlannerConstant.BUDGET_FILTER_THREE;
                        }
                        adapter.clearCollection();
                        tourdetails = databaseHelper.applyFilter(currentSortingOrder, mSelectedBudget);
                        adapter.setPlaceCollection(tourdetails);
                    }
                });


                radioGroupDuration.setVisibility(View.GONE);

                if (mSelectedDurationId != -1){
                    radioGroupDuration.check(mSelectedDurationId);
                }

                checkBoxDuration.setChecked(mDurationSelected);
                if(mDurationSelected){
                    radioGroupDuration.setVisibility(View.VISIBLE);
                }

                checkBoxDuration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            radioGroupDuration.setVisibility(View.VISIBLE);
                        } else {
                            adapter.updateNumberOfNights(5);
                            radioGroupDuration.setVisibility(View.GONE);
                            mDurationSelected = false;
                            mSelectedDurationId = -1;
                        }
                    }
                });

                radioGroupDuration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        mDurationSelected = true;
                        mSelectedDurationId = checkedId;
                        int numberOfNights = 0;
                        switch (checkedId) {
                            case R.id.rd_duration_3days:
                                numberOfNights = 3;
                                break;
                            case R.id.rd_duration_5days:
                                numberOfNights = 5;
                                break;
                            case R.id.rd_duration_week:
                                numberOfNights = 7;
                                break;
                        }

                        adapter.updateNumberOfNights(numberOfNights);
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
