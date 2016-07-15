package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class LendDiaryEntry extends DiaryEntry{
    @DatabaseField
    private Person lendTo;
    @DatabaseField
    private double lendAmount;
    @DatabaseField
    private Date lendOn;
    @DatabaseField
    private String narration;

    public LendDiaryEntry(){

    }

    public LendDiaryEntry(String entryTitle, String entryDescription, Timestamp entryCreatedOn,
                          Timestamp entryLastUpdatedOn, double entryLocationLat, double entryLocationLon,
                          Person lendTo, double lendAmount, Date lendOn, String narration) {
        super(entryTitle, entryDescription, entryCreatedOn, entryLastUpdatedOn, entryLocationLat, entryLocationLon);
        this.lendTo = lendTo;
        this.lendAmount = lendAmount;
        this.lendOn = lendOn;
        this.narration = narration;
    }

    public Person getLendTo() {
        return lendTo;
    }

    public void setLendTo(Person lendTo) {
        this.lendTo = lendTo;
    }

    public double getLendAmount() {
        return lendAmount;
    }

    public void setLendAmount(double lendAmount) {
        this.lendAmount = lendAmount;
    }

    public Date getLendOn() {
        return lendOn;
    }

    public void setLendOn(Date lendOn) {
        this.lendOn = lendOn;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
