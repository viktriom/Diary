package com.sonu.diary.domain.bean;

import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
public class BorrowDiaryEntry extends DiaryEntry {
    private Person borrowedFrom;
    private double amount;
    private Date borrowedOn;
    private String narration;

    public BorrowDiaryEntry(){
    }

    public BorrowDiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                            Date entryLastUpdatedOn, double entryLocationLat,
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
