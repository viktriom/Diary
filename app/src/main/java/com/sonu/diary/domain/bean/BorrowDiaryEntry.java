package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class BorrowDiaryEntry {
    @DatabaseField(id=true, columnName = "borrowentry_id")
    private Long borrowEntryId;
    @DatabaseField(foreign = true)
    private Person borrowedFrom;
    @DatabaseField
    private double amount;
    @DatabaseField
    private Date borrowedOn;
    @DatabaseField
    private String narration;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private DiaryEntry diaryEntry;

    public BorrowDiaryEntry(){
    }

    public BorrowDiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                            Date entryLastUpdatedOn, double entryLocationLat,
                            double entryLocationLon, Person borrowedFrom, double amount,
                            Date borrowedOn, String narration) {
        diaryEntry = new DiaryEntry(entryTitle, entryDescription, entryCreatedOn, entryLastUpdatedOn, entryLocationLat, entryLocationLon);
        this.borrowEntryId = entryCreatedOn.getTime();
        this.borrowedFrom = borrowedFrom;
        this.amount = amount;
        this.borrowedOn = borrowedOn;
        this.narration = narration;
    }

    public long getBorrowEntryId() {
        return borrowEntryId;
    }

    public void setBorrowEntryId(long borrowEntryId) {
        this.borrowEntryId = borrowEntryId;
        diaryEntry.setEntryId(borrowEntryId);
    }

    public Person getBorrowedFrom() {
        return borrowedFrom;
    }

    public void setBorrowedFrom(Person borrowedFrom) {
        this.borrowedFrom = borrowedFrom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBorrowedOn() {
        return borrowedOn;
    }

    public void setBorrowedOn(Date borrowedOn) {
        this.borrowedOn = borrowedOn;
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
