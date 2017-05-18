package com.sonu.diary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.database.DatabaseOperations;
import com.sonu.diary.database.DatabaseOperationsImpl;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.domain.bean.DiaryPage;
import com.sonu.diary.util.DateUtils;

import java.sql.Timestamp;
import java.util.Date;

public class GeneralEntry extends AppCompatActivity {

    private DiaryEntry de = new DiaryEntry();
    private EditText entryTitle;
    private EditText entryLocation;
    private EditText entryDescription;
    private TextView creationTimeLabel;
    private TextView lastModifiedTimeLabel;
    private DatabaseOperations dbOpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_entry);
        dbOpers = new DatabaseOperationsImpl(this.getApplicationContext());
        init();
    }

    private void init() {
        Log.i(GeneralEntry.class.getName(), "Initializing the general entry.");
        Log.i(GeneralEntry.class.getName(), "Initializing UI Objects.");
        entryTitle = (EditText)findViewById(R.id.txtEntryTitle);
        entryLocation = (EditText)findViewById(R.id.txtEntryLocation);
        entryDescription = (EditText)findViewById(R.id.txtGeneralEntryDetail);
        creationTimeLabel = (TextView) findViewById(R.id.lblCreationTime);
        lastModifiedTimeLabel = (TextView) findViewById(R.id.lblLastModifiedOn);
        Log.i(GeneralEntry.class.getName(), "Done Initializing UI Objects.");

        Timestamp creationTime = DateUtils.getCurrentTimestamp();
        creationTimeLabel.setText(DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(text + DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        de.setEntryId(creationTime.getTime());
        de.setEntryCreatedOn(new Date(creationTime.getTime()));
        de.setEntryLastUpdatedOn(new Date(creationTime.getTime()));
    }

    public void saveGeneralEntry(View view){
        Log.i(GeneralEntry.class.getName(), "Populating the DiaryEntry bean.");
        DiaryPage currentPage = (DiaryPage)dbOpers.findById(DiaryPage.class,Long.parseLong(
                DateUtils.getStringDateFromTimestampInFormat(DateUtils.getCurrentTimestamp(),
                        DateUtils.NUMERIC_DATE_FORMAT_WITHOUT_SEPARATORS)));

        de.setDiaryPage(currentPage);
        de.setEntryDescription(entryDescription.getText().toString());
        de.setEntryTitle(entryTitle.getText().toString());
        de.setLocation(entryLocation.getText().toString());
        if(validationPassed()) {
            Log.i(GeneralEntry.class.getName(), "Saving the generic Entry.");
            dbOpers.create(DiaryEntry.class, de);
            finish();
        }else{
            Log.i(GeneralEntry.class.getName(), "Data Validation on the entered data failed.");
        }
    }

    private boolean validationPassed() {
        if(de.getEntryId() == null || de.getEntryId() < 0){
            return false;
        }
        if(de.getEntryCreatedOn() == null || de.getEntryLastUpdatedOn() == null){
            return false;
        }
        if(de.getDiaryPage() == null) {
            return false;
        }
        if(de.getEntryDescription() == null || de.getEntryDescription().isEmpty()) {
            return false;
        }
        if(de.getEntryTitle() == null || de.getEntryTitle().isEmpty()) {
            return false;
        }
        return !(de.getLocation() == null || de.getLocation().isEmpty());
    }
}
