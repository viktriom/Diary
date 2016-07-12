package com.sonu.diary.domain.bean;

import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
public class DiaryEntry {
    private String entryTitle;
    private String entryDescription;
    private Date entryCreatedOn;
    private Date entryLastUpdatedOn;
    private double entryLocationLat;
    private double entryLocationLon;
    private int securityClearanceLevel;

    public DiaryEntry(){

    }

    public DiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn, Date entryLastUpdatedOn, double entryLocationLat, double entryLocationLon) {
        this.entryTitle = entryTitle;
        this.entryDescription = entryDescription;
        this.entryCreatedOn = entryCreatedOn;
        this.entryLastUpdatedOn = entryLastUpdatedOn;
        this.entryLocationLat = entryLocationLat;
        this.entryLocationLon = entryLocationLon;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public Date getEntryCreatedOn() {
        return entryCreatedOn;
    }

    public void setEntryCreatedOn(Date entryCreatedOn) {
        this.entryCreatedOn = entryCreatedOn;
    }

    public Date getEntryLastUpdatedOn() {
        return entryLastUpdatedOn;
    }

    public void setEntryLastUpdatedOn(Date entryLastUpdatedOn) {
        this.entryLastUpdatedOn = entryLastUpdatedOn;
    }

    public double getEntryLocationLat() {
        return entryLocationLat;
    }

    public void setEntryLocationLat(double entryLocationLat) {
        this.entryLocationLat = entryLocationLat;
    }

    public double getEntryLocationLon() {
        return entryLocationLon;
    }

    public void setEntryLocationLon(double entryLocationLon) {
        this.entryLocationLon = entryLocationLon;
    }
}
