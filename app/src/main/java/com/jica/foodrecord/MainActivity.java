package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDatabaseCallback{

    private static final String TAG = "MainActivity";


    MapFragment fragmentMap;
    TimeLineFragment fragmentTimeLine;

    BottomNavigationView bottomNavigation;

    FoodDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentMap = new MapFragment();
        fragmentTimeLine = new TimeLineFragment();



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
            Log.d(TAG, "Book database is open.");
        }else {
            Log.d(TAG, "Book database is not open.");
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
    public void insert(String date, String title, String contents, String location) {
            database.insertRecord(date, title, contents, location);
    }

    @Override
    public ArrayList<FoodItem> selectAll() {
        ArrayList<FoodItem> result = database.selectAll();
        return result;
    }


//    public void onTabSelected(int position){
//        if (position == 0){
//            bottomNavigation.setSelectedItemId(R.id.map);
//
//        }else if(position == 1){
//            bottomNavigation.setSelectedItemId(R.id.timeLine);
//        }
//    }



}