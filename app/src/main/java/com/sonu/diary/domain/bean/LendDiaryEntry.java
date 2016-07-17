package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class LendDiaryEntry {
    @DatabaseField(id = true, columnName = "lenddiary_id")
    private Long lendEntryId;
    @DatabaseField(foreign = true)
    private Person lendTo;
    @DatabaseField
    private double lendAmount;
    @DatabaseField
    private Date lendOn;
    @DatabaseField
    private String narration;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private DiaryEntry diaryEntry;

    public LendDiaryEntry(){

    }

    public LendDiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                          Date entryLastUpdatedOn, double entryLocationLat, double entryLocationLon,
                          Person lendTo, double lendAmount, Date lendOn, String narration) {
        diaryEntry = new DiaryEntry(entryTitle, entryDescription, entryCreatedOn, entryLastUpdatedOn, entryLocationLat, entryLocationLon);
        this.lendEntryId = entryCreatedOn.getTime();
        this.lendTo = lendTo;
        this.lendAmount = lendAmount;
        this.lendOn = lendOn;
        this.narration = narration;
    }

    public long getLendEntryId() {
        return lendEntryId;
    }

    public void setLendEntryId(long lendEntryId) {
        this.lendEntryId = lendEntryId;
        this.diaryEntry.setEntryId(lendEntryId);
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

    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }
}
