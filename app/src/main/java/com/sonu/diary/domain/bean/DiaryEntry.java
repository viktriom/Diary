package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.Date;

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
    private Date entryCreatedOn;
    @DatabaseField
    private Date entryLastUpdatedOn;
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

    public DiaryEntry(String entryTitle, String entryDescription, Date entryCreatedOn,
                      Date entryLastUpdatedOn, double entryLocationLat, double entryLocationLon) {
        this.entryId = entryCreatedOn.getTime();
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
        setEntryId(entryCreatedOn.getTime());
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
