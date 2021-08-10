package com.jica.foodrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    Button btnBack;
    Button btnTheme;
    Button btnTextStyle;
    Button btnPassword;
    Button btnBackup;
    Button btnDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        btnBack = findViewById(R.id.btnBackSetting);
        btnTheme = findViewById(R.id.btnTheme);
        btnTextStyle = findViewById(R.id.btnTextStyle);
        btnPassword = findViewById(R.id.btnPassword);
        btnBackup = findViewById(R.id.btnBackup);
        btnDelete = findViewById(R.id.btnDelete);


        //버튼 클릭 액티비티 전환

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BackupActivity.class);
                startActivity(intent);

            }
        });

        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThemeActivity.class);
                startActivity(intent);
            }
        });

        btnTextStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StyleActivity.class);
                startActivity(intent);
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BackupActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataAllDeleteActivity.class);
                startActivity(intent);
            }
        });




    }
}