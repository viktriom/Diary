package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.security.Timestamp;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class ExpenditureDiaryEntry extends DiaryEntry {
    @DatabaseField
    private String narration;
    @DatabaseField
    private double amount;

    public ExpenditureDiaryEntry(){

    }

    public ExpenditureDiaryEntry(String entryTitle, String entryDescription, Timestamp entryCreatedOn,
                                 Timestamp entryLastUpdatedOn, double entryLocationLat,
                                 double entryLocationLon, String narration, double amount) {
        super(entryTitle, entryDescription, entryCreatedOn, entryLastUpdatedOn, entryLocationLat, entryLocationLon);
        this.narration = narration;
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
