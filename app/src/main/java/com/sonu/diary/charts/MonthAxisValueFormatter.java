package com.sonu.diary.charts;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.Month;

public class MonthAxisValueFormatter extends ValueFormatter {

    private final String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public MonthAxisValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value) {

        int month = (int) value;

        String monthName = mMonths[(month-1) % mMonths.length];

        return monthName;

    }
}
