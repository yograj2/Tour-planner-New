package com.example.yogra.tourplanner;

import android.app.Application;
import android.util.Log;

public class TourApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application","Successfully created");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("Application","Terminated successfully");
    }
}
