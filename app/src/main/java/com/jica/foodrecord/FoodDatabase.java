package com.jica.foodrecord;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;


public class FoodDatabase {

    public static final String TAG = "FoodDatabase";

    private static FoodDatabase database;

    public static String DATABASE_NAME = "food.db";

    public static String TABLE_FOOD_INFO = "FOOD_INFO";

    public static int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    private Context context;

    private FoodDatabase(Context context){
        this.context = context;
    }

    public static FoodDatabase getInstance(Context context){
        if(database == null){
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
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
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
        public DatabaseHelper( Context context) {
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
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  DATE TEXT, "
                    + "  TITLE TEXT, "
                    + "  CONTENTS TEXT, "
                    + "  LOCATION TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }


            insertRecord(_db, "삼월칠일", "용성양", "맛있음", "전주");





        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {   // version 1

            }
        }

        private void insertRecord(SQLiteDatabase _db, String date, String title, String contents , String location) {
            try {
                _db.execSQL( "insert into " + TABLE_FOOD_INFO + "(DATE, TITLE, CONTENTS, LOCATION) values ('" + date + "', '" + title + "', '" + contents + "', '" + location + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }



    }


    public void insertRecord(String date, String title, String contents, String location){
        try{
            db.execSQL( "insert into " + TABLE_FOOD_INFO + "(DATE, TITLE, CONTENTS, LOCATION) values ('" + date + "', '" + title + "', '" + contents + "', '" + location + "');" );
        }catch (Exception ex){
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<FoodItem> selectAll() {
        ArrayList<FoodItem> result = new ArrayList<FoodItem>();

        try{
            Cursor cursor = db.rawQuery("select DATE, TITLE, CONTENTS, LOCATION from " + TABLE_FOOD_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                String data = cursor.getString(0);
                String title = cursor.getString(1);
                String contents = cursor.getString(2);
                String location = cursor.getString(3);

                FoodItem item = new FoodItem(data, title, contents, location);
                result.add(item);

            }

        }catch (Exception ex){
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
        return result;

    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }


}
