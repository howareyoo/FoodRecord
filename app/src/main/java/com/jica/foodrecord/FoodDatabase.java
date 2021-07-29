package com.jica.foodrecord;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static java.sql.DriverManager.println;

public class FoodDatabase {
    private  static final String TAG = "FoodDatabase";


    private static  FoodDatabase database;


    public  static String DATABASE_NAME = "food.db";

    public static String TABLE_FOOD_INFO = "FOOD_INFO";

    public static int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;;

    private SQLiteDatabase db;

    private Context context;

    private FoodDatabase(Context context){
        this.context = context;
    }

    public static FoodDatabase getInstance(Context context) {
        if (database == null){
            database = new FoodDatabase(context);
        }
        return database;
    }

    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;

    }

    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try{
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());

        } catch (Exception ex){
            Log.e(TAG, "Exception in executeQuery",ex);
        }
        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            println("creating table [" + TABLE_FOOD_INFO + "].");

            String DROP_SQL = "drop table if exists " + TABLE_FOOD_INFO;
            try {
                _db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "create table " + TABLE_FOOD_INFO + "("
                    + " _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " DATA TEXT "
                    + " TITLE TEXT "
                    + " PICTURE TEXT DEFAULT "
                    + " TIMEPICKER TEXT"
                    + " CONTENTS TEXT "
                    + " LOCATION TEXT "
                    + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";



        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 2) {   // version 1

            }
        }

        private void insertRecord(SQLiteDatabase _db, String title, String picture, String timePicker, String contents, String location) {
            try {
                _db.execSQL( "insert into " + TABLE_FOOD_INFO + "(TITLE, PICTURE, TIMEPICKER, CONTENTS, LOCATION) values ('" + title + "', '" + picture + "','" + timePicker + "', '" + contents + "', '"+location+"');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

    }

    public void insertRecord(String title, String picture, String timePicker,String contents, String location) {
        try {
            db.execSQL( "insert into " + TABLE_FOOD_INFO + "(TITLE, PICTURE, TIMEPICKER,CONTENTS, LOCATION) values ('" + title + "', '" + picture + "','" + timePicker + "', '" + contents + "', '"+location+"');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }


    private void println(String msg) {
        Log.d(TAG, msg);
    }





}
