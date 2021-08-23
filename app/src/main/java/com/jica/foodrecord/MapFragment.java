package com.jica.foodrecord;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    private MapView mapView;
    MarkerOptions myMarker;

    RecyclerView recyclerView;
    MapInformationAdapter adapter;

    OnDatabaseCallback callback;

    Context context;


    private  FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;



    @Override
    public void onAttach(@NonNull  Context context) {

        callback = (OnDatabaseCallback) getActivity();



        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup flContainer, Bundle savedInstanceState){
         ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_map, flContainer, false);

        context = flContainer.getContext();
//        inflater = (LayoutInflater) getSystemService(context.INPUT_SERVICE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);



        //지도생성

         mapView = rootview.findViewById(R.id.mapView2);
         mapView.onCreate(savedInstanceState);
         mapView.getMapAsync(this::onMapReady);






        //리사이클러뷰

        recyclerView = (RecyclerView) rootview.findViewById(R.id.rvMapInfo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(60);
        recyclerView.addItemDecoration(spaceDecoration);

        adapter = new MapInformationAdapter();
        recyclerView.setAdapter(adapter);

        ArrayList<FoodItem> result = callback.selectAll();
        adapter.setItems(result);


        //리사이클러뷰 클릭시 장소 이동

        adapter.setItemClickListener(new OnMapInformationClickListener() {
            @Override
            public void onItemClick(MapInformationAdapter.ViewHolder holder, View view, int position) {


               FoodItem item = adapter.getItem(position);

               String findLocation = item.getLocation();


                Toast.makeText(context, "click" + findLocation, Toast.LENGTH_LONG).show();

               Location location = getLocationFromAddress(context, findLocation.toString());
               showCurrentLocation(location);




            }
        });








        return rootview;
    }


    //지도

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




    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if ((addresses == null) || (addresses.size() == 0)) {
                return null;
            }
            Address addressLoc = addresses.get(0);

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }



    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        String msg = "Latitutde : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        //화면 확대, 숫자가 클수록 확대
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        //마커 찍기
        Location targetLocation = new Location("");
        targetLocation.setLatitude(curPoint.latitude);
        targetLocation.setLongitude(curPoint.longitude);
        showMyMarker(targetLocation);
    }








    private void showMyMarker(Location location) {
        myMarker = new MarkerOptions();
        myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer));
        map.addMarker(myMarker);
    }



    }