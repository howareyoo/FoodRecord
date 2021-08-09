package com.jica.foodrecord;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public interface OnDatabaseCallback {

    public ArrayList<FoodItem> selectAll();


    public void insert(String date, String title, String picture, float ratingbar, String time, String contents, String location);

    public void delete(int _id);
}
