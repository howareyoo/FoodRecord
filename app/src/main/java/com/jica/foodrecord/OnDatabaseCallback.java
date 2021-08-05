package com.jica.foodrecord;

import java.util.ArrayList;

public interface OnDatabaseCallback {



   public void insert(String date, String title, String picture, float ratingbar, String time, String contents, String location);

    public ArrayList<FoodItem> selectAll();

}
