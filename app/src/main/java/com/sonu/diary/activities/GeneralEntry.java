package com.sonu.diary.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.google.gson.Gson;
import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.domain.enums.ListType;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.util.AppUtil;
import com.sonu.diary.util.DateUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneralEntry extends AbstractActivity {

    private DiaryEntry de;
    private Spinner ddEntryTitle;
    private Spinner ddEntryEvent;
    private EditText entryLocation;
    private EditText entryDescription;
    private TextView creationTimeLabel;
    private TextView lastModifiedTimeLabel;
    private TextView txtEntryActionTime;
    private TextView txtExpenditureAmount;
    private Spinner ddExpenditureSource;
    private Switch swhIsSharable;
    private Boolean editMode = false;

    private ArrayAdapter<String> paymentModeAdapter;
    private ArrayAdapter<String> entryTitleAdapter;
    private ArrayAdapter<String> entryEventAdapter;

    Map<String, String> titleMap = new HashMap<>();

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

    protected void onPostResume() {
        super.onPostResume();
        prepareDropDownList();
    }

    public void prepareDropDownList(){
        List<String> titleLst = new LinkedList<>();
        titleLst.add("---Select Category---");
        titleLst.add("Add New");

        List<String> paymentModeLst = new LinkedList<>();
        paymentModeLst.add("---Select Payment Mode---");
        paymentModeLst.add("Add New");

        List<String> eventLst = new LinkedList<>();
        eventLst.add("---Select Event Name---");
        eventLst.add("Add New");

        if(null == entryTitleAdapter) {
            entryTitleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new LinkedList<>());
            entryTitleAdapter.addAll(titleLst);
            entryTitleAdapter.addAll(CacheManager.getEntryTitleCache().getEntryTitleNames());
        } else {
            entryTitleAdapter.clear();
            entryTitleAdapter.addAll(titleLst);
            entryTitleAdapter.addAll(CacheManager.getEntryTitleCache().getEntryTitleNames());
        }

        if(null == entryEventAdapter) {
            entryEventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new LinkedList<>());
            entryEventAdapter.addAll(eventLst);
            entryEventAdapter.addAll(CacheManager.getEntryEventCache().getEventNames());
        } else {
            entryEventAdapter.clear();
            entryEventAdapter.addAll(eventLst);
            entryEventAdapter.addAll(CacheManager.getEntryEventCache().getEventNames());
        }

        if(null == paymentModeAdapter) {
            paymentModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new LinkedList<>());
            paymentModeAdapter.addAll(paymentModeLst);
            paymentModeAdapter.addAll(CacheManager.getPaymentModeCache().getPaymentModeDescriptions());
        } else {
            paymentModeAdapter.clear();
            paymentModeAdapter.addAll(paymentModeLst);
            paymentModeAdapter.addAll(CacheManager.getPaymentModeCache().getPaymentModeDescriptions());
        }
        ddEntryTitle.setAdapter(entryTitleAdapter);
        ddExpenditureSource.setAdapter(paymentModeAdapter);
        ddEntryEvent.setAdapter(entryEventAdapter);
    }

    private void init(){
        Log.i(GeneralEntry.class.getName(), "Initializing UI Objects.");

        ddEntryTitle = findViewById(R.id.ddTitle);
        ddEntryEvent = findViewById(R.id.ddEntryEvent);
        entryLocation = findViewById(R.id.txtEntryLocation);
        entryDescription = findViewById(R.id.txtGeneralEntryDetail);
        creationTimeLabel = findViewById(R.id.lblCreationTime);
        lastModifiedTimeLabel = findViewById(R.id.lblLastModifiedOn);
        txtEntryActionTime = findViewById(R.id.txtEntryActionTime);
        txtExpenditureAmount = findViewById(R.id.txtExpenditure);
        ddExpenditureSource = findViewById(R.id.ddExpenditureSource);
        swhIsSharable = findViewById(R.id.swhIsSharable);

        prepareDropDownList();

        Context context = this;
        ddEntryTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)parent.getItemAtPosition(position);
                if("Add New".equals(selected)){
                    Intent intent = new Intent(context, ListItemInput.class);
                    intent.putExtra("listType", ListType.ENTRY_TITLE.name());
                    intent.putExtra("message", "Enter the new Title to be added:");
                    startActivity(intent);
                } else if(position > 1){
                    titleMap.put("category", selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ddExpenditureSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)parent.getItemAtPosition(position);
                if("Add New".equals(selected)){
                    Intent intent = new Intent(context, ListItemInput.class);
                    intent.putExtra("listType", ListType.PAYMENT_MODES.name());
                    intent.putExtra("message", "Enter the new Payment Mode to be added:");
                    startActivity(intent);
                } else if(position > 1) {
                    de.setEntryExpenditureSource(selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ddEntryEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)parent.getItemAtPosition(position);
                if("Add New".equals(selected)){
                    Intent intent = new Intent(context, ListItemInput.class);
                    intent.putExtra("listType", ListType.ENTRY_EVENT.name());
                    intent.putExtra("message", "Enter the new Event to be added:");
                    startActivity(intent);
                } else if(position > 1) {
                    titleMap.put("eventName", selected);
                }
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
            ddEntryTitle.setSelection(entryTitleAdapter.getPosition(map.get("category")), false);
            ddEntryEvent.setSelection(entryEventAdapter.getPosition(map.get("eventName")), false);
        } else {
            ddEntryTitle.setSelection(entryTitleAdapter.getPosition(de.getEntryTitle()), false);
        }
        entryLocation.setText(de.getLocation());
        entryDescription.setText(de.getEntryDescription());
        if (null != de.getEntryExpenditure()){
            txtExpenditureAmount.setText(de.getEntryExpenditure().toString());
            ddExpenditureSource.setSelection(paymentModeAdapter.getPosition(de.getEntryExpenditureSource()), false);
        }
        txtEntryActionTime.setText(DateUtils.getStringDateFromTimestampInFormat(de.getEntryActionTime(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        creationTimeLabel.setText(DateUtils.getStringDateFromTimestampInFormat(de.getEntryCreatedOn(), DateUtils.DEFAULT_TIMESTAMP_FORMAT));
        String text = lastModifiedTimeLabel.getText().toString();
        lastModifiedTimeLabel.setText(String.format("%s%s", text, DateUtils.getStringDateFromTimestampInFormat(de.getEntryLastUpdatedOn(), DateUtils.DEFAULT_TIMESTAMP_FORMAT)));
        de.setEntryLastUpdatedOn(DateUtils.getCurrentTimestamp());
        swhIsSharable.setChecked(de.isSharable());
        ddEntryTitle.setSelection(1, false);
    }

    public void saveGeneralEntry(View view){
        Log.i(GeneralEntry.class.getName(), "Populating the DiaryEntry bean.");

        DiaryPage currentPage = CacheManager.getDiaryCache().getDiaryPageForDate(de.getEntryActionTime());
        de.setDiaryPage(currentPage);
        de.setEntryDescription(entryDescription.getText().toString());
        de.setEntryTitle((String) ddEntryTitle.getSelectedItem());
        de.setLocation(entryLocation.getText().toString());
        String expAmount = null == txtExpenditureAmount.getText()?null:txtExpenditureAmount.getText().toString();
        if(null != expAmount && !expAmount.isEmpty() && !expAmount.equals("0") && AppUtil.isInteger(expAmount, 10)){
            de.setExpenseAdded(true);
            de.setEntryExpenditure(Integer.parseInt(txtExpenditureAmount.getText().toString()));
            de.setEntryExpenditureSource((String) ddExpenditureSource.getSelectedItem());
        } else {
            de.setExpenseAdded(false);
        }

        Gson gson = new Gson();
        String title = gson.toJson(titleMap);
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
}
