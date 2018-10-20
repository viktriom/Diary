package com.sonu.diary.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
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
    private Boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_entry);
        long entryId = this.getIntent().getLongExtra("entryId", -1);
        init();
        if(-1 == entryId){
            editMode = false;
            initForNew();
        } else {
            editMode = true;
            initForEdit(entryId);
        }
    }

    private void init(){
        Log.i(GeneralEntry.class.getName(), "Initializing UI Objects.");
        entryTitle = (EditText)findViewById(R.id.txtEntryTitle);
        entryLocation = (EditText)findViewById(R.id.txtEntryLocation);
        entryDescription = (EditText)findViewById(R.id.txtGeneralEntryDetail);
        creationTimeLabel = (TextView) findViewById(R.id.lblCreationTime);
        lastModifiedTimeLabel = (TextView) findViewById(R.id.lblLastModifiedOn);
        txtEntryActionTime = (TextView) findViewById(R.id.txtEntryActionTime);
        txtExpenditureAmount = (TextView)findViewById(R.id.txtExpenditure);
        txtExpenditureSource = (TextView)findViewById(R.id.txtExpenditureSource);
        swhIsSharable = (Switch)findViewById(R.id.swhIsSharable);
    }

    private void initForNew() {
        Log.i(GeneralEntry.class.getName(), "Initializing the general entry.");
        de = new DiaryEntry();
        Log.i(GeneralEntry.class.getName(), "Done Initializing UI Objects.");
        swhIsSharable.setChecked(false);
        Timestamp creationTime = DateUtils.getCurrentTimestamp();
        de.setEntryId(creationTime.getTime());
        de.setEntryCreatedOn(creationTime);
        de.setEntryLastUpdatedOn(creationTime);
        de.setEntryActionTime(creationTime);
        txtEntryActionTime.setText(DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        creationTimeLabel.setText(DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(text + DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
    }

    public void initForEdit(long entryId) {
        Log.i(GeneralEntry.class.getName(), "Editing the object at position : " + entryId);
        de = CacheManager.getDiaryCache().getDiaryEntryForId(entryId);
        entryTitle.setText(de.getEntryTitle());
        entryLocation.setText(de.getLocation());
        entryDescription.setText(de.getEntryDescription());
        if (null != de.getEntryExpenditure()){
            txtExpenditureAmount.setText(de.getEntryExpenditure().toString());
            txtExpenditureSource.setText(de.getEntryExpenditureSource());
        }
        txtEntryActionTime.setText(DateUtils.getStringDateFromTimestampInFormat(de.getEntryActionTime(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        creationTimeLabel.setText(DateUtils.getStringDateFromTimestampInFormat(de.getEntryCreatedOn(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(text + DateUtils.getStringDateFromTimestampInFormat(de.getEntryLastUpdatedOn(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        de.setEntryLastUpdatedOn(DateUtils.getCurrentTimestamp());
    }

    public void saveGeneralEntry(View view){
        Log.i(GeneralEntry.class.getName(), "Populating the DiaryEntry bean.");

        DiaryPage currentPage = CacheManager.getDiaryCache().getDiaryPageForDate(de.getEntryActionTime());
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
            CacheManager.getDiaryCache().addOrUpdateEntry(de, editMode);
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
        super.showDateTimePicker(view, DateUtils.getCurrentTimestamp());
    }

    @Override
    public void performActionAfterDateTimeUpdate(Timestamp ts) {
        super.performActionAfterDateTimeUpdate(ts);
        de.setEntryActionTime(ts);
    }
}
