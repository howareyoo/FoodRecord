package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.pedro.library.AutoPermissions;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity implements OnMapReadyCallback {

    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    Button btnDate;
    TextView editTextDate;
    Button btnComplete;
    CalendarView calendarView;
    Button btnDone;

    RatingBar ratingBar;

    ImageView ivTakePhoto;
    ImageView ivTakePicture;
//    private static final int REQUEST_CODE = 0;


    //    private MapView mapView;
    SupportMapFragment mapFragment;
    GoogleMap map;
    TextView tvSearchCurrentLocation;
    EditText etSearch;
    MarkerOptions myMarker;


    Button btnTimePicker;
    Calendar calendar = Calendar.getInstance();
    int alarmHour = calendar.get(Calendar.HOUR), alarmMinute = calendar.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        btnDate = findViewById(R.id.btnDate);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_calendar, null, false);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        btnComplete = view.findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();

            }
        });

        editTextDate = view.findViewById(R.id.editTextDate);
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

//
//                String week = new SimpleDateFormat("EE").format();
//                Log.d("TAG", week);


                String date = year + "." + ( month + 1 ) + "." + dayOfMonth;
                Log.d("TAG", date);


                btnDate.setText(date);


            }
        });


        btnTimePicker = findViewById(R.id.btnTimePicker);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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


//        mapView = findViewById(R.id.edMapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this::onMapReady);




        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvSearchCurrentLocation = findViewById(R.id.tvSearchCurrentLocation);
        etSearch = findViewById(R.id.etSearch);

        tvSearchCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSearch.getText().toString().length() > 0){
                    Location location = getLocationFromAddress(getApplicationContext(), etSearch.getText().toString());

                    showCurrentLocation(location);

                }
            }
        });



//        MapsInitializer.initialize(this);


        ivTakePicture = findViewById(R.id.ivTakePicture);
        ivTakePhoto = findViewById(R.id.ivTakePhoto);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        }


        ivTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ivTakePhoto.setVisibility(View.INVISIBLE);
                Intent intent = new Intent();
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);


            }
        });


        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });


        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 101 && resultCode == RESULT_OK) {

                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap bitmap2 = resizeBitmap(1024, bitmap);

                inputStream.close();
                ivTakePicture.setImageBitmap(bitmap2);
//                Uri uri = data.getData();
//
//                ContentResolver resolver = getContentResolver();
//                Cursor cursor = resolver.query(uri, null, null, null, null);
//                cursor.moveToNext();
//
//                int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
//                String source = cursor.getString(index);
//
//                Bitmap bitmap = BitmapFactory.decodeFile(source);
//
////
//
//                ivTakePicture.setImageBitmap(bitmap);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


//
//        if(requestCode == REQUEST_CODE){
//            if(requestCode == RESULT_OK){
//                try{
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//
//                    ivTakePicture.setImageBitmap(img);
//                }catch (Exception e){
//
//                }
//            }
//
//            else if(resultCode == RESULT_CANCELED){
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//            }
//
//        }


    }

    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
        double ratio = (double) targetWidth / (double) source.getWidth();

        int targetHeight = (int) ( source.getHeight() + ratio );

        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);

        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
//            googleMap.addMarker(new MarkerOptions().position(new LatLng(33.38,126.55)).title("Maker"));
    }


    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0)) {
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


    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            long minTime = 1000;    //갱신 시간
            float minDistance = 0;  //갱신에 필요한 최소 거리

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    showCurrentLocation(location);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        String msg = "Latitutde : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        //화면 확대, 숫자가 클수록 확대
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        //마커 찍기
        Location targetLocation = new Location("");
        targetLocation.setLatitude(37.4937);
        targetLocation.setLongitude(127.0643);
        showMyMarker(targetLocation);
    }


    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void showMyMarker(Location location) {
        if(myMarker == null) {
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.title("◎ 내위치\n");
            myMarker.snippet("여기가 어디지?");
            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer));
            map.addMarker(myMarker);
        }
    }







//
//    @Override
//    public void onStart() {
//        mapView.onStart();
//        super.onStart();
//    }
//
//
//    @Override
//    public void onResume() {
//        mapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        mapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        mapView.onStop();
//        super.onStop();
//    }
//
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        mapView.onSaveInstanceState(outState);
//        super.onSaveInstanceState(outState);
//    }
//
//
//    @Override
//    public void onDestroy() {
//        mapView.onDestroy();
//        super.onDestroy();
//
//    }
//
//    @Override
//    public void onLowMemory() {
//        mapView.onLowMemory();
//        super.onLowMemory();
//    }
//


//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//
//
//        map = googleMap;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);
//
//
//    }




}