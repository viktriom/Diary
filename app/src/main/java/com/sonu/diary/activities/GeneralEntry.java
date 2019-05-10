package com.sonu.diary.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.database.DatabaseOperations;
import com.sonu.diary.database.DatabaseOperationsImpl;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.domain.EntryEvent;
import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.domain.enums.ListType;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.util.AppUtil;
import com.sonu.diary.util.DateUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class GeneralEntry extends AbstractActivity {

    private DiaryEntry de;
    private AutoCompleteTextView acEntryTitle;
    private AutoCompleteTextView acEntryEvent;
    private EditText entryLocation;
    private EditText entryDescription;
    private TextView creationTimeLabel;
    private TextView lastModifiedTimeLabel;
    private TextView txtEntryActionTime;
    private TextView txtExpenditureAmount;
    private AutoCompleteTextView acExpenditureSource;
    private Switch swhIsSharable;
    private Boolean editMode = false;

    private ArrayAdapter<String> paymentModeAdapter;
    private ArrayAdapter<String> entryTitleAdapter;
    private ArrayAdapter<String> entryEventAdapter;

    private Map<String, String> titleMap = new HashMap<>();

    private DatabaseOperations dbOper;

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

    public void prepareDropDownList(){
        entryTitleAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, CacheManager.getEntryTitleCache().getEntryTitleNames());
        entryEventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CacheManager.getEntryEventCache().getEventNames());
        paymentModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CacheManager.getPaymentModeCache().getPaymentModeDescriptions());
        acEntryTitle.setAdapter(entryTitleAdapter);
        acExpenditureSource.setAdapter(paymentModeAdapter);
        acEntryEvent.setAdapter(entryEventAdapter);
    }

    private void init(){
        Log.i(GeneralEntry.class.getName(), "Initializing UI Objects.");

        dbOper = new DatabaseOperationsImpl(this);

        acEntryTitle = findViewById(R.id.acTitle);
        acEntryEvent = findViewById(R.id.acEntryEvent);
        entryLocation = findViewById(R.id.txtEntryLocation);
        entryDescription = findViewById(R.id.txtGeneralEntryDetail);
        creationTimeLabel = findViewById(R.id.lblCreationTime);
        lastModifiedTimeLabel = findViewById(R.id.lblLastModifiedOn);
        txtEntryActionTime = findViewById(R.id.txtEntryActionTime);
        txtExpenditureAmount = findViewById(R.id.txtExpenditure);
        acExpenditureSource = findViewById(R.id.acExpenditureSource);
        swhIsSharable = findViewById(R.id.swhIsSharable);

        prepareDropDownList();



        acEntryTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    acEntryTitle.showDropDown();

            }
        });


        acExpenditureSource.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    acExpenditureSource.showDropDown();

            }
        });

        acEntryEvent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    acEntryEvent.showDropDown();

            }
        });

        acEntryTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        acExpenditureSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        acEntryEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initForNew() {
        Log.i(GeneralEntry.class.getName(), "Initializing the general entry.");
        de = new DiaryEntry();
        de.setSharable(false);
        Log.i(GeneralEntry.class.getName(), "Done Initializing UI Objects.");
        swhIsSharable.setChecked(false);
        Timestamp creationTime = DateUtils.getCurrentTimestamp();
        de.setEntryId(creationTime.getTime());
        de.setEntryCreatedOn(creationTime);
        de.setEntryLastUpdatedOn(creationTime);
        de.setEntryActionTime(creationTime);
        txtEntryActionTime.setText(DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        creationTimeLabel.setText(String.format("Created:%s", DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT)));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(String.format("%s%s", text, DateUtils.getStringDateFromTimestampInFormat(creationTime, DateUtils.DEFAULT_TIMESTAMP_FORMAT)));
    }

    public void initForEdit(long entryId) {
        Log.i(GeneralEntry.class.getName(), "Editing the object at position : " + entryId);
        de = CacheManager.getDiaryCache().getDiaryEntryForId(entryId);
        if(de.getEntryTitle().contains("eventName")){
            Gson gson = new Gson();
            Map<String,String> map = gson.<Map<String, String>>fromJson(de.getEntryTitle(), Map.class);
            acEntryTitle.setText(map.get("category") == null ? "": map.get("category"));
            acEntryEvent.setText(map.get("eventName") == null ? "" : map.get("eventName"));
        } else {
            acEntryTitle.setSelection(entryTitleAdapter.getPosition(de.getEntryTitle()));
        }
        entryLocation.setText(de.getLocation());
        entryDescription.setText(de.getEntryDescription());
        if (null != de.getEntryExpenditure()){
            txtExpenditureAmount.setText(de.getEntryExpenditure().toString());
            acExpenditureSource.setText(de.getEntryExpenditureSource());
        }
        txtEntryActionTime.setText(DateUtils.getStringDateFromTimestampInFormat(de.getEntryActionTime(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        creationTimeLabel.setText(DateUtils.getStringDateFromTimestampInFormat(de.getEntryCreatedOn(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(String.format("%s%s", text, DateUtils.getStringDateFromTimestampInFormat(de.getEntryLastUpdatedOn(), DateUtils.DEFAULT_TIMESTAMP_FORMAT)));
        de.setEntryLastUpdatedOn(DateUtils.getCurrentTimestamp());
        swhIsSharable.setChecked(de.isSharable());
    }

    public void saveGeneralEntry(View view){
        Log.i(GeneralEntry.class.getName(), "Populating the DiaryEntry bean.");

        String title = acEntryTitle.getText().toString();
        String event = acEntryEvent.getText().toString();
        titleMap.put("eventName", title);
        titleMap.put("category", event);
        addValueToList(ListType.ENTRY_TITLE, title);
        addValueToList(ListType.ENTRY_EVENT, event);
        DiaryPage currentPage = CacheManager.getDiaryCache().getDiaryPageForDate(de.getEntryActionTime());
        de.setDiaryPage(currentPage);
        de.setEntryDescription(entryDescription.getText().toString());
        de.setLocation(entryLocation.getText().toString());
        String expAmount = null == txtExpenditureAmount.getText()?null:txtExpenditureAmount.getText().toString();
        if(null != expAmount && !expAmount.isEmpty() && !expAmount.equals("0") && AppUtil.isInteger(expAmount, 10)){
            de.setExpenseAdded(true);
            de.setEntryExpenditure(Integer.parseInt(txtExpenditureAmount.getText().toString()));
            String expSource = acExpenditureSource.getText().toString();
            de.setEntryExpenditureSource(expSource);
            addValueToList(ListType.PAYMENT_MODES, expSource);
        } else {
            de.setExpenseAdded(false);
        }

        Gson gson = new Gson();
        title = gson.toJson(titleMap);
        de.setEntryTitle(title);
        de.setSyncStatus(SyncStatus.P.name());
        if(validationPassed()) {
            Log.i(GeneralEntry.class.getName(), "Saving the generic Entry.");
            CacheManager.getDiaryCache().addOrUpdateEntry(de, editMode);
            finish();
        }else{
            Log.i(GeneralEntry.class.getName(), "Data Validation on the entered data failed.");
        }
    }

    private boolean validationPassed() {
        Toast toast =  Toast.makeText(this, "", Toast.LENGTH_SHORT);
        if(de.getEntryId() == null || de.getEntryId() < 0){
            return false;
        }
        if(de.getEntryCreatedOn() == null || de.getEntryLastUpdatedOn() == null){
            return false;
        }
        if(de.getDiaryPage() == null) {
            return false;
        }

        if(titleMap.isEmpty() || !titleMap.containsKey("category")){
            toast.setText("Please select a Category for this entry.");
            toast.show();
            return false;
        }

        if(de.getLocation() == null || de.getLocation().isEmpty()){
            toast.setText("Please enter a location.");
            toast.show();
            return false;
        }

        if(de.getEntryDescription() == null || de.getEntryDescription().isEmpty()) {
            toast.setText("Please enter a description.");
            toast.show();
            return false;
        }

        if(null == de.getEntryExpenditureSource() || de.getEntryExpenditureSource().isEmpty()){
            toast.setText("Please select a Expenditure source");
            toast.show();
            return false;
        }
        return true;
    }

    public void showPicker(View view) {
        super.showDateTimePicker(view, DateUtils.getCurrentTimestamp());
    }

    @Override
    public void performActionAfterDateTimeUpdate(Timestamp ts) {
        super.performActionAfterDateTimeUpdate(ts);
        de.setEntryActionTime(ts);
    }

    public void isSharableTouched(View view) {
        Switch swh = (Switch)view;
        de.setSharable(swh.isChecked());
    }

    public void addValueToList(ListType listType, String value) {
        if(null == value || value.isEmpty())
            return;
        if(ListType.ENTRY_TITLE == listType && entryTitleAdapter.getPosition(value) < 0 ) {
            EntryTitle title = new EntryTitle();
            title.setTitle(value);
            dbOper.create(listType.getTypeClass(), title);
            CacheManager.getEntryTitleCache().loadCache();
            acEntryTitle.setAdapter(entryTitleAdapter);
        } else if( ListType.PAYMENT_MODES == listType && paymentModeAdapter.getPosition(value) < 0){
            PaymentMode paymentMode = new PaymentMode();
            paymentMode.setDescription(value);
            dbOper.create(listType.getTypeClass(), paymentMode);
            CacheManager.getPaymentModeCache().loadCache();
            acExpenditureSource.setAdapter(paymentModeAdapter);
        } else if( ListType.ENTRY_EVENT == listType && entryTitleAdapter.getPosition(value) < 0){
            EntryEvent entryEvent = new EntryEvent();
            entryEvent.setEventName(value);
            dbOper.create(listType.getTypeClass(), entryEvent);
            CacheManager.getEntryEventCache().loadCache();
            acEntryEvent.setAdapter(entryEventAdapter);
        }
    }
}
