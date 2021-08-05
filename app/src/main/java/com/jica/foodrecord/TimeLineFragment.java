package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class TimeLineFragment extends Fragment {

    RecyclerView recyclerView;
    TimeLineAdapter adapter;
    OnDatabaseCallback callback;

    Context context;
//    OnTabItemSelectedListener listener;





    @Override
    public void onAttach(@NonNull  Context context) {
        super.onAttach(context);

        callback = (OnDatabaseCallback) getActivity();
//
//        this.context = context;
//        if(context instanceof OnTabItemSelectedListener){
//            listener = (OnTabItemSelectedListener) context;
//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
//
//        if (context != null){
//            context = null;
//            listener = null;
//        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup flContainer, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_time_line, flContainer, false);





        recyclerView  = rootView.findViewById(R.id.rvTimeLine);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(25);
        recyclerView.addItemDecoration(spaceDecoration);


        adapter = new TimeLineAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<FoodItem> result = callback.selectAll();
        adapter.setItems(result);




        FloatingActionButton fabMenu = (FloatingActionButton)rootView.findViewById(R.id.fabMenu);
        FloatingActionButton fabSetting = (FloatingActionButton)rootView.findViewById(R.id.fabSetting);
        FloatingActionButton fabStats = (FloatingActionButton)rootView.findViewById(R.id.fabStats);
//        FloatingActionButton fabPlus = (FloatingActionButton)rootView.findViewById(R.id.fabPlus);


        fabMenu.setOnClickListener(new View.OnClickListener() {

            boolean isVisible;

            @Override
            public void onClick(View v) {

                isVisible = !isVisible;

                if(isVisible){
                    fabSetting.setVisibility(View.VISIBLE);
                    fabStats.setVisibility(View.VISIBLE);
//                    fabPlus.setVisibility(View.VISIBLE);
                }else {
                    fabSetting.setVisibility(View.INVISIBLE);
                    fabStats.setVisibility(View.INVISIBLE);
//                    fabPlus.setVisibility(View.INVISIBLE);

                }
            }
        });


//        fabPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),EditActivity.class);
//                startActivity(intent);
//            }
//        });

        fabStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StatsActivity.class);
                startActivity(intent);
            }
        });

        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });






        return rootView;



    }




}