package com.sonu.diary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sonu.diary.R;

public class RoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
    }

    public void saveRoutineEntry(View view) {
        Log.i(RoutineActivity.class.getName(),"Save Entry Called.");
    }
}
