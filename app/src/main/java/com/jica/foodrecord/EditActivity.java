//package com.jica.foodrecord;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CalendarView;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//
//
//
//import java.io.InputStream;
//
//import java.util.Calendar;
//
//import java.util.List;
//
//public class EditActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    String[] permission_list = {
//            Manifest.permission.READ_EXTERNAL_STORAGE
//    };
//
//
//
//
//    Button btnDate;
//    TextView editTextDate;
//    Button btnComplete;
//    CalendarView calendarView;
//    Button btnDone;
//
//    EditText editTextTextPersonName;
//
//    RatingBar ratingBar;
//
//    ImageView ivTakePhoto;
//    ImageView ivTakePicture;
//
//    EditText etPersonnel;
//    CheckBox checkBoxDrink;
//    EditText editText;
////    private static final int REQUEST_CODE = 0;
//
//
//    SupportMapFragment mapFragment;
//    GoogleMap map;
//
//    EditText etSearch;
//    MarkerOptions myMarker;
//    Button btnSearch;
//
//
//
//    Context context;
//
//
//    Button btnTimePicker;
//    Calendar calendar = Calendar.getInstance();
//    int alarmHour = calendar.get(Calendar.HOUR), alarmMinute = calendar.get(Calendar.MINUTE);
//
//
//
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit);
//        context = this;
//
//
//
//        //객체찾기
//
//        btnDate = findViewById(R.id.btnDate);
//        btnTimePicker = findViewById(R.id.btnTimePicker);
//
//        etSearch = findViewById(R.id.etSearch);
//        btnSearch = findViewById(R.id.btnSearch);
//
//        ivTakePicture = findViewById(R.id.ivTakePicture);
//        ivTakePhoto = findViewById(R.id.ivTakePictureIcon);
//        ratingBar = findViewById(R.id.ratingBar);
//        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
//
//        btnDone = findViewById(R.id.btnDone);
//
//        etPersonnel = findViewById(R.id.etPersonnel);
//        checkBoxDrink = findViewById(R.id.checkBoxDrink);
//
//
//
//
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.fragment_bottom_sheet_calendar, null, false);
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        bottomSheetDialog.setContentView(view);
//
//        btnDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetDialog.show();
//            }
//        });
//
//        btnComplete = view.findViewById(R.id.btnComplete);
//        btnComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                bottomSheetDialog.dismiss();
//
//            }
//        });
//
//        editTextDate = view.findViewById(R.id.editTextDate);
//        calendarView = view.findViewById(R.id.calendarView);
//
//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//
////
////                String week = new SimpleDateFormat("EE").format();
////                Log.d("TAG", week);
//
//
//                String date = year + "." + (month + 1) + "." + dayOfMonth;
//                Log.d("TAG", date);
//
//
//                btnDate.setText(date);
//
//
//            }
//        });
//
//
//
//        btnTimePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        hourOfDay = view.getHour();
//                        minute = view.getMinute();
//                        btnTimePicker.setText(hourOfDay + ":" + minute);
//
//                    }
//                }, alarmHour, alarmMinute, true);
//                timePickerDialog.show();
//
//            }
//        });
//
//
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//
//
//
//
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (etSearch.getText().toString().length() > 0) {
//                    Location location = getLocationFromAddress(getApplicationContext(), etSearch.getText().toString());
//
//                    showCurrentLocation(location);
//
//                }
//            }
//        });
//
//
//
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(permission_list, 0);
//        }
//
//
//        ivTakePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ivTakePhoto.setVisibility(View.INVISIBLE);
//                Intent intent = new Intent();
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 101);
//
//
//            }
//        });
//
//
//
//
//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                ratingBar.getRating();
//
//            }
//        });
//
//
//
//
//
//        //data 저장 버튼
//
//
//
//
//        btnDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                String date = btnDate.getText().toString();
//                String title = editTextTextPersonName.getText().toString();
//                String contents = editText.getText().toString();
//                String location = etSearch.getText().toString();
//
//                FoodDatabase database = FoodDatabase.getInstance(context);
//                database.insertRecord(date, title, contents, location);
//
//
//
//
//            }
//        });
//
//
//    }
//
//
//
//
//
//
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        try {
//            if (requestCode == 101 && resultCode == RESULT_OK) {
//
//                InputStream inputStream = getContentResolver().openInputStream(data.getData());
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                Bitmap bitmap2 = resizeBitmap(1024, bitmap);
//
//
//                inputStream.close();
//                ivTakePicture.setImageBitmap(bitmap2);
//
//
////                Uri uri = data.getData();
////
////                ContentResolver resolver = getContentResolver();
////                Cursor cursor = resolver.query(uri, null, null, null, null);
////                cursor.moveToNext();
////
////                int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
////                String source = cursor.getString(index);
////
////                Bitmap bitmap = BitmapFactory.decodeFile(source);
////
//////
////
////                ivTakePicture.setImageBitmap(bitmap);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////
////        if(requestCode == REQUEST_CODE){
////            if(requestCode == RESULT_OK){
////                try{
////                    InputStream in = getContentResolver().openInputStream(data.getData());
////
////                    Bitmap img = BitmapFactory.decodeStream(in);
////                    in.close();
////
////                    ivTakePicture.setImageBitmap(img);
////                }catch (Exception e){
////
////                }
////            }
////
////            else if(resultCode == RESULT_CANCELED){
////                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
////            }
////
////        }
//
//
//    }
//
//    public Bitmap resizeBitmap(int targetWidth, Bitmap source) {
//        double ratio = (double) targetWidth / (double) source.getWidth();
//
//        int targetHeight = (int) (source.getHeight() + ratio);
//
//        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
//
//        if (result != source) {
//            source.recycle();
//        }
//        return result;
//    }
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
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
////
//    }
//
//
//    private Location getLocationFromAddress(Context context, String address) {
//        Geocoder geocoder = new Geocoder(context);
//        List<Address> addresses;
//        Location resLocation = new Location("");
//        try {
//            addresses = geocoder.getFromLocationName(address, 5);
//            if ((addresses == null) || (addresses.size() == 0)) {
//                return null;
//            }
//            Address addressLoc = addresses.get(0);
//
//            resLocation.setLatitude(addressLoc.getLatitude());
//            resLocation.setLongitude(addressLoc.getLongitude());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resLocation;
//    }
//
////
////    private void requestMyLocation() {
////        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////        try {
////            long minTime = 1000;    //갱신 시간
////            float minDistance = 0;  //갱신에 필요한 최소 거리
////
////            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListener() {
////                @Override
////                public void onLocationChanged(Location location) {
////                    showCurrentLocation(location);
////                }
////
////                @Override
////                public void onStatusChanged(String s, int i, Bundle bundle) {
////
////                }
////
////                @Override
////                public void onProviderEnabled(String s) {
////
////                }
////
////                @Override
////                public void onProviderDisabled(String s) {
////
////                }
////            });
////        } catch (SecurityException e) {
////            e.printStackTrace();
////        }
////    }
//
//    private void showCurrentLocation(Location location) {
//        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
//        String msg = "Latitutde : " + curPoint.latitude
//                + "\nLongitude : " + curPoint.longitude;
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//
//        //화면 확대, 숫자가 클수록 확대
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
//
//        //마커 찍기
//        Location targetLocation = new Location("");
//        targetLocation.setLatitude(curPoint.latitude);
//        targetLocation.setLongitude(curPoint.longitude);
//        showMyMarker(targetLocation);
//    }
//
//
//    private void checkDangerousPermissions() {
//        String[] permissions = {
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_WIFI_STATE
//        };
//
//        int permissionCheck = PackageManager.PERMISSION_GRANTED;
//        for (int i = 0; i < permissions.length; i++) {
//            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
//            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//                break;
//            }
//        }
//
//        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
//                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
//            } else {
//                ActivityCompat.requestPermissions(this, permissions, 1);
//            }
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
//
//
//    private void showMyMarker(Location location) {
//        if (myMarker == null) {
//            myMarker = new MarkerOptions();
//            myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
//            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer));
//            map.addMarker(myMarker);
//        }
//    }
//
//
//}
