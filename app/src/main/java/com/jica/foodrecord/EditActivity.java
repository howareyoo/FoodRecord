package com.jica.foodrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.pedro.library.AutoPermissions;

import java.io.InputStream;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements OnMapReadyCallback {

    String [] permission_list = {
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


    private MapView mapView;

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



                String date = year + "."+ (month + 1) + "." + dayOfMonth;
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
               },alarmHour, alarmMinute, true);
               timePickerDialog.show();

            }
        });







        mapView = findViewById(R.id.edMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);



        ivTakePicture = findViewById(R.id.ivTakePicture);
        ivTakePhoto = findViewById(R.id.ivTakePhoto);
//

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
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

        try{
            if(requestCode == 101 && resultCode == RESULT_OK){

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

        }catch (Exception e){
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

    public Bitmap resizeBitmap(int targetWidth, Bitmap source){
        double ratio = (double)targetWidth / (double)source.getWidth();

        int targetHeight = (int)(source.getHeight() + ratio);

        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth,targetHeight, false);

        if(result != source){
            source.recycle();
        }
        return  result;
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






    }
}