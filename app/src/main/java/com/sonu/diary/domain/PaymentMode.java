package com.sonu.diary.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "payment_modes")
public class PaymentMode extends AbstractDomain {

    @DatabaseField
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getKey() {
        return description;
    }
}
