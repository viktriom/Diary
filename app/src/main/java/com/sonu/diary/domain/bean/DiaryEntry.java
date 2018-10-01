package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class DiaryEntry {
    @DatabaseField(id = true, columnName = "diaryentry_id")
    private Long entryId;
    @DatabaseField
    private String entryTitle;
    @DatabaseField
    private String entryDescription;
    @DatabaseField
    private Timestamp entryCreatedOn;
    @DatabaseField
    private Timestamp entryLastUpdatedOn;
    @DatabaseField
    private Timestamp entryActionTime;
    @DatabaseField
    private double entryLocationLat;
    @DatabaseField
    private double entryLocationLon;
    @DatabaseField
    private String location;
    @DatabaseField
    private int securityClearanceLevel;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private DiaryPage diaryPage;

    public DiaryEntry(){

    }

    public DiaryEntry(String entryTitle, String entryDescription, Timestamp entryCreatedOn,
                      Timestamp entryLastUpdatedOn,Timestamp entryActionTime, double entryLocationLat, double entryLocationLon) {
        this.entryId = entryCreatedOn.getTime();
        this.entryTitle = entryTitle;
        this.entryDescription = entryDescription;
        this.entryCreatedOn = entryCreatedOn;
        this.entryLastUpdatedOn = entryLastUpdatedOn;
        this.entryActionTime = entryActionTime;
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
        setEntryId(entryCreatedOn.getTime());
    }

    public Timestamp getEntryLastUpdatedOn() {
        return entryLastUpdatedOn;
    }

    public void setEntryLastUpdatedOn(Timestamp entryLastUpdatedOn) {
        this.entryLastUpdatedOn = entryLastUpdatedOn;
    }

    public Timestamp getEntryActionTime() {
        return entryActionTime;
    }

    public void setEntryActionTime(Timestamp entryActionTime) {
        this.entryActionTime = entryActionTime;
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

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
