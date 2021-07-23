package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener{

    MapFragment fragmentMap;
    TimeLineFragment fragmentTimeLine;

    BottomNavigationView bottomNavigation;

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

    }

    public void onTabSelected(int position){
        if (position == 0){
            bottomNavigation.setSelectedItemId(R.id.map);

        }else if(position == 1){
            bottomNavigation.setSelectedItemId(R.id.timeLine);
        }
    }



}