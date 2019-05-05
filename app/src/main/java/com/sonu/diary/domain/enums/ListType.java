package com.sonu.diary.domain.enums;

import com.sonu.diary.domain.EntryEvent;
import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;

public enum ListType {
    PAYMENT_MODES(PaymentMode.class),
    ENTRY_TITLE(EntryTitle.class),
    ENTRY_EVENT(EntryEvent.class);

    Class cls;
    ListType(Class cls){
        this.cls = cls;
    }

    public Class getTypeClass(){
        return cls;
    }
}
