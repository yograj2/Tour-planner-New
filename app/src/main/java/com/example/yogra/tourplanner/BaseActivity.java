package com.example.yogra.tourplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.yogra.tourplanner.Util.PreferenceHelper;

public abstract class BaseActivity extends AppCompatActivity {

    public PreferenceHelper mPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        mPreferenceHelper = PreferenceHelper.getInstance(this);
        init();
    }

    protected abstract void init();
}
