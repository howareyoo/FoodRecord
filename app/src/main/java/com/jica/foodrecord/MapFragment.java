package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    private MapView mapView;

    RecyclerView recyclerView;
    MapInformationAdapter adapter;





    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup flContainer, Bundle savedInstanceState){
         ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_map, flContainer, false);

         mapView = rootview.findViewById(R.id.mapView2);
         mapView.onCreate(savedInstanceState);
         mapView.getMapAsync(this::onMapReady);



        recyclerView = (RecyclerView) rootview.findViewById(R.id.rvMapInfo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(60);
        recyclerView.addItemDecoration(spaceDecoration);

        adapter = new MapInformationAdapter();

        adapter.addItem(new MapInformation("부송국수"));
        adapter.addItem(new MapInformation("용성양"));
        adapter.addItem(new MapInformation("후켄"));


        recyclerView.setAdapter(adapter);




        return rootview;
    }

    @Override
    public void onStart() {
        mapView.onStart();
        super.onStart();
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }


    @Override
    public void onSaveInstanceState(@NonNull  Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull  GoogleMap googleMap) {

        map = googleMap;



    }
}