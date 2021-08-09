package com.jica.foodrecord;

import android.content.Context;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;


public class BottomSheetCalendarFragment extends BottomSheetDialogFragment {

    private Context context;
    Button btnComplete;
    TextView tvChoseDate;

    CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_bottom_sheet_calendar, container, false);

       btnComplete = view.findViewById(R.id.btnComplete);
       calendarView = view.findViewById(R.id.calendarView);
       tvChoseDate = view.findViewById(R.id.tvChoseDate);




       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {



               String date = (month + 1) + "." + dayOfMonth;

               Log.d("TAG", date);


               tvChoseDate.setText(date);







           }
       });




       btnComplete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


              Bundle bundle = new Bundle();
              bundle.putString("choseDate",tvChoseDate.getText().toString());
              FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
              EditFragment editFragment = new EditFragment();
              editFragment.setArguments(bundle);
              transaction.replace(R.id.flContainer, editFragment);
              transaction.commit();

              dismiss();





           }
       });


        return view;

    }



}