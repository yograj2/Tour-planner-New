package com.example.yogra.tourplanner.SQLiteDatabase.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.yogra.tourplanner.Util.Place;
import com.example.yogra.tourplanner.Util.User;

import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "place.db";
    public static final int DATABASE_VERSION =  1;
    public static final String PLACE_NAME =  "place_name";
    public static final String PLACE_DESCRIPTION = "place_description";
    public static final String PLACE_SEIGHTSEEING = "place_seightseeing";
    public static final String COST_PER_NIGHT = "cost_per_night";
    public static final String PLACE_ID = "place_id";
    public static final String TABLE_NAME = "place";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            String query = "create table "+TABLE_NAME +" ( "+PLACE_ID+" integer primary key, "+PLACE_NAME+" text, "+PLACE_DESCRIPTION+" text, "+PLACE_SEIGHTSEEING+" text, "+COST_PER_NIGHT+" Integer )";
            db.execSQL(query);
            Log.d(TAG,"Query sucessfully run" +query);

        }
        catch(Exception e)
        {
            Log.d(TAG,"Excception "+e.getMessage());
        }

    }


    public void insertPlace(Place place){
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLACE_NAME,place.getTourPlace());
        contentValues.put(PLACE_DESCRIPTION,place.getTourDescription());
        contentValues.put(PLACE_SEIGHTSEEING,place.getSightSeeing());
        contentValues.put(COST_PER_NIGHT,place.getNightCharge());
        contentValues.put(PLACE_ID,place.getId());
        long data =  writableDatabase.insert(TABLE_NAME,null,contentValues);

        Log.d(TAG,"wriatable data prited" +data);
    }

    public void insertPlaceList(List<Place> placeList) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        for (Place place : placeList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PLACE_NAME,place.getTourPlace());
            contentValues.put(PLACE_DESCRIPTION,place.getTourDescription());
            contentValues.put(PLACE_SEIGHTSEEING,place.getSightSeeing());
            contentValues.put(COST_PER_NIGHT,place.getNightCharge());
            long data =  writableDatabase.insert(TABLE_NAME,null,contentValues);
        }
    }


   /* //SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
   // SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

    Cursor cursor = sqLiteDatabase.rawQuery("select " +PLACE_NAME+","+PLACE_DESCRIPTION+" from "+DATABASE_NAME+"",new String[]{});
*/

  /* String Data(Place place){
       SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
       String query1 = "select " +PLACE_NAME+","+PLACE_DESCRIPTION+" from "+DATABASE_NAME+"";
       Cursor cursor = sqLiteDatabase.rawQuery(query1,null);
       if (cursor.getCount()>0){
           cursor.moveToFirst();
       }
       return query1;*/

/*
       Cursor cursor = sqLiteDatabase.rawQuery("select " +PLACE_NAME+","+PLACE_DESCRIPTION+" from "+DATABASE_NAME+"");
*/




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

