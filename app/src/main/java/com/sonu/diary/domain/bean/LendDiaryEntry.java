package com.sonu.diary.domain.bean;

import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
public class LendDiaryEntry extends DiaryEntry{
    private Person lendTo;
    private double lendAmount;
    private Date lendOn;
    private String narration;

    public LendDiaryEntry(){

    }

    public LendDiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                          Date entryLastUpdatedOn, double entryLocationLat, double entryLocationLon,
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
