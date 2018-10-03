package com.sonu.diary.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.caches.DiaryCache;
import com.sonu.diary.database.DatabaseOperations;
import com.sonu.diary.database.DatabaseOperationsImpl;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.domain.bean.DiaryPage;
import com.sonu.diary.util.AppUtil;
import com.sonu.diary.util.DateUtils;

import java.sql.Timestamp;

public class GeneralEntry extends AbstractActivity {

    private DiaryEntry de;
    private EditText entryTitle;
    private EditText entryLocation;
    private EditText entryDescription;
    private TextView creationTimeLabel;
    private TextView lastModifiedTimeLabel;
    private TextView txtEntryActionTime;
    private TextView txtExpenditureAmount;
    private TextView txtExpenditureSource;
    private Switch swhIsSharable;
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
        de = new DiaryEntry();
        entryTitle = (EditText)findViewById(R.id.txtEntryTitle);
        entryLocation = (EditText)findViewById(R.id.txtEntryLocation);
        entryDescription = (EditText)findViewById(R.id.txtGeneralEntryDetail);
        creationTimeLabel = (TextView) findViewById(R.id.lblCreationTime);
        lastModifiedTimeLabel = (TextView) findViewById(R.id.lblLastModifiedOn);
        txtEntryActionTime = (TextView) findViewById(R.id.txtEntryActionTime);
        txtExpenditureAmount = (TextView)findViewById(R.id.txtExpenditure);
        txtExpenditureSource = (TextView)findViewById(R.id.txtExpenditureSource);
        swhIsSharable = (Switch)findViewById(R.id.swhIsSharable);
        Log.i(GeneralEntry.class.getName(), "Done Initializing UI Objects.");

        swhIsSharable.setChecked(false);
        Timestamp creationTime = DateUtils.getCurrentTimestamp();
        txtEntryActionTime.setText(DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        creationTimeLabel.setText(DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(text + DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        de.setEntryId(creationTime.getTime());
        de.setEntryCreatedOn(creationTime);
        de.setEntryLastUpdatedOn(creationTime);
        de.setEntryActionTime(creationTime);
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
        String expAmount = null == txtExpenditureAmount.getText()?null:txtExpenditureAmount.getText().toString();
        if(null != expAmount && !expAmount.isEmpty() && !expAmount.equals("0") && AppUtil.isInteger(expAmount, 10)){
            de.setExpenseAdded(true);
            de.setEntryExpenditure(Integer.parseInt(txtExpenditureAmount.getText().toString()));
            de.setEntryExpenditureSource(txtExpenditureSource.getText().toString());
        } else {
            de.setExpenseAdded(false);
        }
        if(validationPassed()) {
            Log.i(GeneralEntry.class.getName(), "Saving the generic Entry.");
            DiaryCache.addNewDiaryEntry(de, this.getApplicationContext());
            MainActivity mainActivity = (MainActivity)getIntent().getSerializableExtra("mainActivity");
            if(null != mainActivity) mainActivity.notifyMainClass();
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

    public void showPicker(View view) {
        super.showDateTimePicker(view);
    }
}
