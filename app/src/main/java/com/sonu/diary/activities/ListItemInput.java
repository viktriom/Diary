package com.sonu.diary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sonu.diary.R;
import com.sonu.diary.caches.CacheManager;
import com.sonu.diary.database.DatabaseOperations;
import com.sonu.diary.database.DatabaseOperationsImpl;
import com.sonu.diary.domain.EntryEvent;
import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.domain.enums.ListType;

public class ListItemInput extends AbstractActivity {

    private EditText txtInput;
    private TextView lblMessage;

    private ListType listType = ListType.ENTRY_TITLE;

    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_input);
        String lstType = this.getIntent().getStringExtra("listType");
        message = this.getIntent().getStringExtra("message");
        if(null != lstType)
            listType = ListType.valueOf(lstType);
        init();
    }

    private void init() {
        txtInput = findViewById(R.id.txtInput);
        lblMessage = findViewById(R.id.lblMessage);
        lblMessage.setText(message);
    }

    public void btnCancelTouched(View view) {
        finish();
    }

    public void btnAddTouched(View view) {
        String text = txtInput.getText().toString();
        DatabaseOperations dbOper = new DatabaseOperationsImpl(this);
        if(ListType.ENTRY_TITLE == listType) {
            EntryTitle title = new EntryTitle();
            title.setTitle(text);
            dbOper.create(listType.getTypeClass(), title);
            CacheManager.getEntryTitleCache().loadCache();
        } else if( ListType.PAYMENT_MODES == listType){
            PaymentMode paymentMode = new PaymentMode();
            paymentMode.setDescription(text);
            dbOper.create(listType.getTypeClass(), paymentMode);
            CacheManager.getPaymentModeCache().loadCache();
        } else if( ListType.ENTRY_EVENT == listType){
            EntryEvent entryEvent = new EntryEvent();
            entryEvent.setEventName(text);
            dbOper.create(listType.getTypeClass(), entryEvent);
            CacheManager.getEntryEventCache().loadCache();
        }
        finish();
    }
}
