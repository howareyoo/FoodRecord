package com.jica.foodrecord;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class FoodRecordStatsColor {

    public static int [] COOL_COLORS = {
            Color.rgb(5, 76, 237), Color.rgb(5, 143, 247),
            Color.rgb(102, 200, 162), Color.rgb(5, 247, 222),
            Color.rgb(16, 190, 224)
    };


    public static List<Integer> createColors(int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(i);
        }

        return result;
    }


}
