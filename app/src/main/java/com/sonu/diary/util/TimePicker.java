package com.sonu.diary.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;

import com.sonu.diary.activities.AbstractActivity;

import java.util.Calendar;

public class TimePicker extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener{

    private int hour;
    private int minute;
    private AbstractActivity caller;

    public void onAttach (Context context){
        super.onAttach(context);
        Bundle bundle = getArguments();
        hour = bundle.getInt("hour");
        minute = bundle.getInt("minute");
        caller = (AbstractActivity) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        caller.updateSelectedTime(hourOfDay, minute);
    }
}
