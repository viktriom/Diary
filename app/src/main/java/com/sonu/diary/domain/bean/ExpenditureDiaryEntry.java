package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class ExpenditureDiaryEntry {
    @DatabaseField(id=true, columnName = "expenditureentry_id")
    private Long expEntryId;
    @DatabaseField
    private String narration;
    @DatabaseField
    private double amount;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private DiaryEntry diaryEntry;

    public ExpenditureDiaryEntry(){

    }

    public ExpenditureDiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                                 Date entryLastUpdatedOn, double entryLocationLat,
                                 double entryLocationLon, String narration, double amount) {
        diaryEntry = new DiaryEntry(entryTitle, entryDescription, entryCreatedOn, entryLastUpdatedOn, entryLocationLat, entryLocationLon);
        this.expEntryId = entryCreatedOn.getTime();
        this.narration = narration;
        this.amount = amount;
    }

    public long getExpEntryId() {
        return expEntryId;
    }

    public void setExpEntryId(long expEntryId) {
        this.expEntryId = expEntryId;
        this.diaryEntry.setEntryId(expEntryId);
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

    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }
}
