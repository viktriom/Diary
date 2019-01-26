package com.sonu.diary.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sonu.diary.domain.enums.SyncStatus;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable(tableName = "diaryentry")
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
    private Integer entryExpenditure;
    @DatabaseField
    private String entryExpenditureSource;
    @DatabaseField
    private boolean isSharable;
    @DatabaseField
    private boolean isExpenseAdded;
    @DatabaseField
    private int securityClearanceLevel;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    @Expose(serialize = false, deserialize = false)
    private DiaryPage diaryPage;
    @DatabaseField
    private String syncStatus = SyncStatus.P.name();
    private long pageId;

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

    public DiaryEntry(Long entryId, String entryTitle, String entryDescription, Timestamp entryCreatedOn, Timestamp entryLastUpdatedOn,
                      Timestamp entryActionTime, double entryLocationLat, double entryLocationLon, String location, Integer entryExpenditure,
                      String entryExpenditureSource, boolean isSharable, boolean isExpenseAdded, int securityClearanceLevel) {
        this.entryId = entryId;
        this.entryTitle = entryTitle;
        this.entryDescription = entryDescription;
        this.entryCreatedOn = entryCreatedOn;
        this.entryLastUpdatedOn = entryLastUpdatedOn;
        this.entryActionTime = entryActionTime;
        this.entryLocationLat = entryLocationLat;
        this.entryLocationLon = entryLocationLon;
        this.location = location;
        this.entryExpenditure = entryExpenditure;
        this.entryExpenditureSource = entryExpenditureSource;
        this.isSharable = isSharable;
        this.isExpenseAdded = isExpenseAdded;
        this.securityClearanceLevel = securityClearanceLevel;
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
        this.pageId = diaryPage.getPageId();
    }

    public long getPageId(){
        return this.pageId;
    }

    public void setPageId(Long pageId){
        this.pageId = pageId;
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

    public Integer getEntryExpenditure() {
        return entryExpenditure;
    }

    public void setEntryExpenditure(Integer entryExpenditure) {
        this.entryExpenditure = entryExpenditure;
    }

    public String getEntryExpenditureSource() {
        return entryExpenditureSource;
    }

    public void setEntryExpenditureSource(String entryExpenditureSource) {
        this.entryExpenditureSource = entryExpenditureSource;
    }

    public boolean isSharable() {
        return isSharable;
    }

    public void setSharable(boolean sharable) {
        isSharable = sharable;
    }

    public boolean isExpenseAdded() {
        return isExpenseAdded;
    }

    public void setExpenseAdded(boolean expenseAdded) {
        isExpenseAdded = expenseAdded;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryEntry that = (DiaryEntry) o;
        return Objects.equals(entryId, that.entryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryId);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public DiaryEntry clone(){
        DiaryEntry de = new DiaryEntry();
        de.setEntryId(this.getEntryId());
        de.setEntryTitle(this.getEntryTitle());
        de.setEntryDescription(this.getEntryDescription());
        de.setEntryCreatedOn(this.getEntryCreatedOn());
        de.setEntryLastUpdatedOn(this.getEntryLastUpdatedOn());
        de.setEntryActionTime(this.getEntryActionTime());
        de.setLocation(this.getLocation());
        de.setEntryExpenditure(this.getEntryExpenditure());
        de.setEntryExpenditureSource(this.getEntryExpenditureSource());
        de.setSharable(this.isSharable());
        de.setExpenseAdded(this.isExpenseAdded());
        de.setPageId(this.getDiaryPage().getPageId());
        de.setSyncStatus(this.getSyncStatus());
        return de;
    }
}
