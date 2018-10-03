package com.sonu.diary.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sonu.diary.util.DatePicker;
import com.sonu.diary.util.DateUtils;
import com.sonu.diary.util.TimePicker;

import java.io.Serializable;
import java.sql.Timestamp;

public class AbstractActivity extends AppCompatActivity implements Serializable {

    private int year, month, dayOfMonth, hour, minute, second=0;
    private View view;
    private Timestamp ts;

    public void showTimePicker(Timestamp ts){
        Bundle bundle = new Bundle();
        bundle.putSerializable("hour", DateUtils.getHoursFromTimestamp(ts));
        bundle.putSerializable("minute", DateUtils.getMinutesFromTimestamp(ts));
        AppCompatDialogFragment newFragment = new TimePicker();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void updateSelectedTime(int hour, int minute){
       this.hour = hour;
       this.minute = minute;

        String strTS = this.dayOfMonth + "/" + this.month + "/" + this.year + " " + this.hour + ":" + this.minute + ":" + this.second;
         Timestamp updatedTs = DateUtils.getTimestampFromStringInFormat(strTS, "dd/MM/yyyy HH:mm:ss");
        ((TextView)view).setText(DateUtils.getStringDateFromTimestampInFormat(updatedTs, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
    }

    public void showDatePicker(Timestamp ts){
        Bundle bundle = new Bundle();
        bundle.putSerializable("year", DateUtils.getYearFromTimestamp(ts));
        bundle.putSerializable("month", DateUtils.getMonthFromTimestamp(ts));
        bundle.putSerializable("dayOfMonth", DateUtils.getDayOfMonthFromTimestamp(ts));
        AppCompatDialogFragment newFragment = new DatePicker();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateSelectedDate(int year, int month, int dayOfMonth){
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        showTimePicker(ts);
    }

    public void showDateTimePicker(View view, Timestamp ts){
        this.view = view;
        this.ts = ts;
        showDatePicker(ts);
    }

    public void showDateTimePicker(View view){
        showDateTimePicker(view, DateUtils.getCurrentTimestamp());
    }

}
