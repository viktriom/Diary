package com.sonu.diary.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatDialogFragment;

import com.sonu.diary.activities.AbstractActivity;

public class DatePicker extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year = DateUtils.getCurrentYear();
    private int month = DateUtils.getCurrentMonth();
    private int dayOfMonth = DateUtils.getCurrentDayOfMonth();
    private AbstractActivity caller;

    public void onAttach (Context context){
        super.onAttach(context);
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        dayOfMonth = bundle.getInt("dayOfMonth");
        caller = (AbstractActivity)context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new DatePickerDialog(getActivity(), this, year, month - 1, dayOfMonth);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        caller.updateSelectedDate(year, month + 1, dayOfMonth);
    }
}
