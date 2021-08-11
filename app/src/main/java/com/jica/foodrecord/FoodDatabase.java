package com.jica.foodrecord;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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
                    + "  PICTURE TEXT, "
                    + "  RATINGBAR FLOAT, "
                    + "  TIME TEXT, "
                    + "  PERSONNEL TEXT, "
                    + "  DRINK TEXT, "
                    + "  CONTENTS TEXT, "
                    + "  LOCATION TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }



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

        private void insertRecord(SQLiteDatabase _db, String date, String title, String picture, float ratingbar, String  time, String personnel, String drink ,String contents , String location) {
            try {
                _db.execSQL( "insert into " + TABLE_FOOD_INFO + "(DATE, TITLE, PICTURE, RATINGBAR, TIME , PERSONNEL, DRINK, CONTENTS, LOCATION) values ('" + date + "', '" + title + "', '" + picture + "' , '" + ratingbar + "','" + time + "','" + personnel + "','" + drink + "','" + contents + "', '" + location + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

      private void deleteRecord(SQLiteDatabase _db, int  _id){
                try{
                   _db.execSQL("DELETE FROM FOOD_INFO WHERE _id=" + _id);
                }catch (Exception ex){
                    Log.e(TAG, "Exception in executing delete SQL.", ex);
                }
      }





    }







    //db 삭제

    public void deleteRecord(int _id){
        try{
            db.execSQL("DELETE FROM FOOD_INFO WHERE _id=" + _id);
            Log.d(TAG, _id + " 자료의 row를 삭제했습니다.");

        }catch (Exception ex){
            Log.e(TAG, "Exception in executing delete SQL.", ex);
        }
    }



    //db 입력

    public void insertRecord(String date, String title,String picture, float ratingbar, String time, String personnel, String drink, String contents, String location){
        try{
            db.execSQL( "insert into " + TABLE_FOOD_INFO + "(DATE, TITLE, PICTURE, RATINGBAR, TIME,PERSONNEL, DRINK, CONTENTS, LOCATION) values ('" + date + "', '" + title + "', '" + picture + "' , '" + ratingbar + "','" + time + "','" + personnel + "','" + drink + "','" + contents + "', '" + location + "');" );
        }catch (Exception ex){
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }



    //모든 db 불러오기

    public ArrayList<FoodItem> selectAll() {
        ArrayList<FoodItem> result = new ArrayList<FoodItem>();

        try{
            Cursor cursor = db.rawQuery("select _id,DATE, TITLE, PICTURE, RATINGBAR, TIME, PERSONNEL, DRINK ,CONTENTS, LOCATION from " + TABLE_FOOD_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                String date = cursor.getString(1);
                String title = cursor.getString(2);
                String picture = cursor.getString(3);
                float ratingbar = cursor.getFloat(4);
                String time = cursor.getString(5);
                String personnel = cursor.getString(6);
                String drink = cursor.getString(7);
                String contents = cursor.getString(8);
                String location = cursor.getString(9);



                FoodItem item = new FoodItem(_id, date, title, picture, ratingbar, time, personnel, drink ,contents, location);
                result.add(item);

            }

        }catch (Exception ex){
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
        return result;

    }



    //오늘 날짜와 같은 db 찾기




    public ArrayList<FoodItem> selectDate() {
        ArrayList<FoodItem> result = new ArrayList<FoodItem>();

        Date currentYear = Calendar.getInstance().getTime();
        SimpleDateFormat today = new SimpleDateFormat("MM.dd", Locale.getDefault());
        String todayDate2 = today.format(currentYear);


        try{

            String sql =  "select _id, DATE, TIME, PERSONNEL, DRINK , LOCATION from FOOD_INFO where DATE = '" + todayDate2 + "'";
            Log.d("TAG", "실행 sql문장 : " + sql );

            Cursor cursor = db.rawQuery(sql  ,  null);
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                String personnel = cursor.getString(3);
                String drink = cursor.getString(4);
                String location = cursor.getString(5);

                FoodItem item = new FoodItem(_id, date, time, personnel, drink , location);
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