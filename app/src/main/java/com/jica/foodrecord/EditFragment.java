package com.jica.foodrecord;



import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;


public class EditFragment extends Fragment implements OnMapReadyCallback {

    //달력
    Button btnDate;
    String date;

    //제목
    EditText editTextTextPersonName;

    //사진
    ImageView ivTakePicture;
    ImageView ivTakePictureIcon;
    Uri uri;

    //별점
    RatingBar ratingBar;

    //시간
    Button btnTimePicker;
    Calendar calendar = Calendar.getInstance();
    int alarmHour = calendar.get(Calendar.HOUR), alarmMinute = calendar.get(Calendar.MINUTE);

    //인원수
    EditText etPersonnel;

    //알콜여부
    CheckBox checkBoxDrink;

    //내용
    EditText editText;

    //지도
    EditText etSearch;
    Button btnSearch;
    MarkerOptions myMarker;
    GoogleMap map;
    MapView mapView;


    //데이터 저장
    Button btnDone;
    OnDatabaseCallback callback;



    Context context;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull  Context context) {
        super.onAttach(context);

        callback = (OnDatabaseCallback) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_edit, container, false);

        context = inflater.getContext();



       //달력 다이얼로그

        btnDate = rootView.findViewById(R.id.btnDate);
        BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetCalendarFragment();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        if(getArguments() != null){
            date = getArguments().getString("choseDate");
            btnDate.setText(date);
        }


        //제목

        editTextTextPersonName = rootView.findViewById(R.id.editTextTextPersonName);


        //사진 불러오기

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }


        ivTakePicture = rootView.findViewById(R.id.ivTakePicture);
        ivTakePictureIcon = rootView.findViewById(R.id.ivTakePictureIcon);

        ivTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivTakePictureIcon.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });

        //별점

        ratingBar = rootView.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.getRating();
            }
        });

        //시간

        btnTimePicker = rootView.findViewById(R.id.btnTimePicker);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourOfDay = view.getHour();
                        minute = view.getMinute();
                        btnTimePicker.setText(hourOfDay + ":" + minute);

                    }
                }, alarmHour, alarmMinute, true);
                timePickerDialog.show();


            }
        });


        //인원수
        etPersonnel = rootView.findViewById(R.id.etPersonnel);



        //알콜여부
        checkBoxDrink = rootView.findViewById(R.id.checkBoxDrink);
        checkBoxDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkResult ;

                if( checkBoxDrink.isChecked()){

                    checkResult = String.valueOf(checkBoxDrink.isChecked());

                    Toast.makeText(context, "checked "+ checkResult , Toast.LENGTH_SHORT).show();

                }else {

                    checkResult = String.valueOf(checkBoxDrink.isChecked());
                    Toast.makeText(context, "checked " + checkResult, Toast.LENGTH_SHORT).show();
                }


                checkBoxDrink.setText(checkResult);


            }
        });

        //내용
        editText = rootView.findViewById(R.id.editText);

        //지도

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);



        btnSearch = rootView.findViewById(R.id.btnSearch);
        etSearch = rootView.findViewById(R.id.etSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etSearch.getText().toString().length() > 0) {
                    Location location = getLocationFromAddress(context, etSearch.getText().toString());

                    showCurrentLocation(location);

                }



            }
        });




        //데이터 저장 그리고 프래그먼트 종료
        btnDone = rootView.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = btnDate.getText().toString();
                String title = editTextTextPersonName.getText().toString();
                String picture = uri.toString();
                float ratingbar = ratingBar.getRating();
                String time = btnTimePicker.getText().toString();
                String personnel = etPersonnel.getText().toString();
                String drink = checkBoxDrink.getText().toString();
                String contents = editText.getText().toString();
                String location = etSearch.getText().toString();

                callback.insert(date,title, picture, ratingbar, time, personnel, drink, contents, location);

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onTabSelected(1);

            }
        });



        return rootView;


    }

    //사진 로드

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            uri = data.getData();
            setImage(uri);
        }

    }

    private void setImage (Uri image){


        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
            ivTakePicture.setImageBitmap(bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
        // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        // permission was granted, yay! Do the
        // contacts-related task you need to do.

                } else {

        // permission denied, boo! Disable the
        // functionality that depends on this permission.
                }
                return;
            }

        // other 'case' lines to check for other
        // permissions this app might request
        }
    }




    //지도

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;

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

/*
    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }

*/


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
        if (myMarker == null) {
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer));
            map.addMarker(myMarker);
        }
    }




}