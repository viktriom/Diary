package com.sonu.diary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.util.DateUtils;

import java.sql.Timestamp;

public class FilterViewController extends AbstractActivity {

    private TextView ptSearchString;
    private TextView tvFromDate;
    private TextView tvToDate;
    private Timestamp fromDate;
    private Timestamp toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_view);
        init();
    }

    private void init() {
        ptSearchString = (TextView) findViewById(R.id.ptSearchString);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);

        Timestamp creationTime = DateUtils.getCurrentTimestamp();
    }

    public void applyEntryFilter(View view) {
        CacheManager.getDiaryCache().clearFilters();
        String searchString = ptSearchString.getText().toString();
        boolean isSearchStringAdded = false;
        if(!searchString.isEmpty()){
            CacheManager.getDiaryCache().addSearchStringToFilter(searchString);
            isSearchStringAdded = true;
        }
        if(null != fromDate && null != toDate){
            CacheManager.getDiaryCache().addDatesToFilterCondition(fromDate, toDate, isSearchStringAdded);
        }
        CacheManager.getDiaryCache().applyFilter();
        finish();
    }

    public void performActionAfterDateTimeUpdate(View view, Timestamp ts) {
        if(view.getId() == R.id.tvFromDate){
            fromDate = ts;
        }

        if(view.getId() == R.id.tvToDate){
            toDate = ts;
        }

    }

    public void onFromDateTouched(View view) {
        super.showDatePicker(view, DateUtils.getCurrentTimestamp());
    }

    public void onToDateTouched(View view) {
        super.showDatePicker(view, DateUtils.getCurrentTimestamp());
    }

    public void clearEntryFilter(View view) {
        CacheManager.getDiaryCache().clearFilters();
        finish();
    }
}
