package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class DiaryEntry {
    @DatabaseField
    private String entryTitle;
    @DatabaseField
    private String entryDescription;
    @DatabaseField
    private Timestamp entryCreatedOn;
    @DatabaseField
    private Timestamp entryLastUpdatedOn;
    @DatabaseField
    private double entryLocationLat;
    @DatabaseField
    private double entryLocationLon;
    @DatabaseField
    private int securityClearanceLevel;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private DiaryPage diaryPage;

    public DiaryEntry(){

    }

    public DiaryEntry(String entryTitle, String entryDescription, Timestamp entryCreatedOn,
                      Timestamp entryLastUpdatedOn, double entryLocationLat, double entryLocationLon) {
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

    public Timestamp getEntryCreatedOn() {
        return entryCreatedOn;
    }

    public void setEntryCreatedOn(Timestamp entryCreatedOn) {
        this.entryCreatedOn = entryCreatedOn;
    }

    public Timestamp getEntryLastUpdatedOn() {
        return entryLastUpdatedOn;
    }

    public void setEntryLastUpdatedOn(Timestamp entryLastUpdatedOn) {
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

    public int getSecurityClearanceLevel() {
        return securityClearanceLevel;
    }

    public void setSecurityClearanceLevel(int securityClearanceLevel) {
        this.securityClearanceLevel = securityClearanceLevel;
    }

    public DiaryPage getDiaryPage() {
        return diaryPage;
    }

    public void setDiaryPage(DiaryPage diaryPage) {
        this.diaryPage = diaryPage;
    }
}
