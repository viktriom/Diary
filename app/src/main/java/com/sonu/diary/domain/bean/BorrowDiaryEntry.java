package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class BorrowDiaryEntry extends DiaryEntry {
    @DatabaseField
    private Person borrowedFrom;
    @DatabaseField
    private double amount;
    @DatabaseField
    private Date borrowedOn;
    @DatabaseField
    private String narration;

    public BorrowDiaryEntry(){
    }

    public BorrowDiaryEntry(String entryTitle, String entryDescription, Timestamp entryCreatedOn,
                            Timestamp entryLastUpdatedOn, double entryLocationLat,
                            double entryLocationLon, Person borrowedFrom, double amount,
                            Date borrowedOn, String narration) {
        super(entryTitle, entryDescription, entryCreatedOn, entryLastUpdatedOn, entryLocationLat, entryLocationLon);
        this.borrowedFrom = borrowedFrom;
        this.amount = amount;
        this.borrowedOn = borrowedOn;
        this.narration = narration;
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
}
