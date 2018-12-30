package com.example.yogra.tourplanner.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private SharedPreferences mSharedPreferences;

    private static PreferenceHelper instance;

    public static final String IS_LOGIN = "is_login";

    private PreferenceHelper(Context context){

        mSharedPreferences = context.getSharedPreferences(IS_LOGIN, Context.MODE_PRIVATE);
    }


    public static PreferenceHelper getInstance(Context context){

        if (instance==null){
            instance = new PreferenceHelper(context);
        }
        return instance;
    }

    public void putBoolean(String key, boolean value){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBoolean(String key, boolean defaultValue){

        return mSharedPreferences.getBoolean(key, defaultValue);
    }

}
