package com.sonu.diary.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sonu.diary.domain.enums.SyncStatus;

import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable(tableName = "diarypage")
public class DiaryPage {

    @Expose(serialize = false, deserialize = false)
    @ForeignCollectionField(eager = true)
    private Collection<DiaryEntry> diaryEntry;
    @DatabaseField(id = true, columnName = "diaryapage_id")
    private long pageId;
    @DatabaseField
    private Date pageDate;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    @Expose(serialize = false, deserialize = false)
    private Diary diary;
    @DatabaseField
    private String syncStatus = SyncStatus.P.name();
    private int diaryId;

    public DiaryPage(){

    }

    public DiaryPage(List<DiaryEntry> diaryEntry, Date pageDate, long pageId, Diary diary) {
        this.diaryEntry = diaryEntry;
        this.pageDate = pageDate;
        this.pageId = pageId;
        this.diary = diary;
    }

    public Collection<DiaryEntry> getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(List<DiaryEntry> diaryEntry) {
        this.diaryEntry = diaryEntry;
    }

    public Date getPageDate() {
        return pageDate;
    }

    public void setPageDate(Date pageDate) {
        this.pageDate = pageDate;
    }

    public void setDiaryEntry(Collection<DiaryEntry> diaryEntry) {
        this.diaryEntry = diaryEntry;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryPage diaryPage = (DiaryPage) o;
        return pageId == diaryPage.pageId &&
                Objects.equals(pageDate, diaryPage.pageDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pageId, pageDate);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public DiaryPage clone(){
        DiaryPage dp = new DiaryPage();
        dp.setDiaryId(this.getDiary().getYear());
        dp.setPageId(this.getPageId());
        dp.setPageDate(this.getPageDate());
        dp.setSyncStatus(this.getSyncStatus());
        return dp;
    }
}
