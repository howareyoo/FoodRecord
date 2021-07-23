package com.jica.foodrecord;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;


import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    PieChart pieChart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);



        pieChart = findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(40f);

        pieChart.setDrawCenterText(true);

        pieChart.setHighlightPerTapEnabled(true);


        ArrayList<PieEntry> ratingBar = new ArrayList<PieEntry>();

        ratingBar.add(new PieEntry(20f, "오리온"));
        ratingBar.add(new PieEntry(30f, "부부상회"));
        ratingBar.add(new PieEntry(10f, "용성양"));
        ratingBar.add(new PieEntry(25f, "올데이잭"));
        ratingBar.add(new PieEntry(15f, "규태네"));



        PieDataSet dataSet = new PieDataSet(ratingBar,"");



//        dataSet.setSliceSpace(3f);
          dataSet.setSelectionShift(5f);



        dataSet.setColors(FoodRecordStatsColor.COOL_COLORS);


        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate();









    }
}