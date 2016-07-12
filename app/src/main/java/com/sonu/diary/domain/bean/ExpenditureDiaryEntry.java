package com.sonu.diary.domain.bean;

import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
public class ExpenditureDiaryEntry extends DiaryEntry {
    private String narration;
    private double amount;

    public ExpenditureDiaryEntry(){

    }

    public ExpenditureDiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                                 Date entryLastUpdatedOn, double entryLocationLat,
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
