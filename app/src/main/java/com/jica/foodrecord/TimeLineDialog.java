package com.jica.foodrecord;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class TimeLineDialog extends Dialog {

    Button btnDataUpdate;
    Button btnDataDelete;
    FoodDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.timeline_dialog);

        btnDataUpdate = findViewById(R.id.btnDataUpdate);
        btnDataDelete = findViewById(R.id.btnDataDelete);

        btnDataDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btnDataUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public TimeLineDialog(@NonNull Context context) {
        super(context);

       this.btnDataDelete = btnDataDelete;
       this.btnDataUpdate = btnDataUpdate;


    }
}
