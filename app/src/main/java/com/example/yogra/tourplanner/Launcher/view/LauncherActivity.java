package com.example.yogra.tourplanner.Launcher.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import com.example.yogra.tourplanner.BaseActivity;
import com.example.yogra.tourplanner.Home.HomeActivity;
import com.example.yogra.tourplanner.Login.view.LoginActivity;
import com.example.yogra.tourplanner.R;
import com.example.yogra.tourplanner.Util.PreferenceHelper;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends BaseActivity {

    Timer timer;
    public static boolean value;
    private static final String TAG = "LauncherActivity";
   // Toolbar toolbar;
    private android.support.v7.widget.Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        /*toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Launcher");*/

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean status = mPreferenceHelper.getBoolean(PreferenceHelper.IS_LOGIN, false);
                Log.d(TAG, "inside Schedule : " + status);
                if (status) {
                    Intent intent= new Intent(LauncherActivity.this,HomeActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent= new Intent(LauncherActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },2000);
        Log.d("Launcher","Successfully done");

    }

    @Override
    protected void init() {

    }
}

