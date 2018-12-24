package com.example.yogra.tourplanner.Launcher.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.yogra.tourplanner.Login.view.LoginActivity;
import com.example.yogra.tourplanner.R;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends AppCompatActivity {

    Timer timer;
    public static boolean value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent= new Intent(LauncherActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
        Log.d("Launcher","Successfully done");

    }
}
