package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDatabaseCallback{

    private static final String TAG = "MainActivity";


    MapFragment fragmentMap;
    TimeLineFragment fragmentTimeLine;
    EditFragment editFragment;

    BottomNavigationView bottomNavigation;

    FoodDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentMap = new MapFragment();
        fragmentTimeLine = new TimeLineFragment();
        editFragment = new EditFragment();



        bottomNavigation = findViewById(R.id.bottomNavigation);



        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                switch (item.getItemId()){
                    case R.id.map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragmentMap).commit();

                        return true;
                    case R.id.timeLine:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragmentTimeLine).commit();

                        return true;

                    case R.id.edit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, editFragment).commit();
                }
                return false;
            }
        });

        bottomNavigation.setSelectedItemId(R.id.map);



        if(database != null){
            database.close();
            database = null;
        }

        database = FoodDatabase.getInstance(this);
        boolean isOpen = database.open();
        if(isOpen){
            Log.d(TAG, "Food database is open.");
        }else {
            Log.d(TAG, "Food database is not open.");
        }


    }


    protected void onDestroy() {
        // close database
        if (database != null) {
            database.close();
            database = null;
        }

        super.onDestroy();
    }

    @Override
    public void insert(String date, String title, String picture, float ratingbar, String time, String personnel, String drink , String contents, String location) {
        database.insertRecord(date, title, picture, ratingbar, time,personnel, drink ,contents,location);
    }

    @Override
    public void delete(int _id) {
        database.deleteRecord(_id);
    }



    @Override
    public ArrayList<FoodItem> selectAll() {

        ArrayList<FoodItem> result = database.selectAll();


        return result;
    }

    @Override
    public ArrayList<FoodItem> selectDate() {

        ArrayList<FoodItem> result = database.selectDate();

        return result;
    }


    public void onTabSelected(int position){
        if (position == 0){
            bottomNavigation.setSelectedItemId(R.id.map);

        }else if(position == 1){
            bottomNavigation.setSelectedItemId(R.id.timeLine);
        }
    }



}