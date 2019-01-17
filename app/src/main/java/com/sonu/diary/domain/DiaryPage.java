package com.sonu.diary.domain;

import com.google.gson.Gson;
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
@DatabaseTable
public class DiaryPage {

    @ForeignCollectionField(eager = true)
    private Collection<DiaryEntry> diaryEntry;
    @DatabaseField(id = true, columnName = "diaryapage_id")
    private long pageId;
    @DatabaseField
    private Date pageDate;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Diary diary;
    @DatabaseField
    private String syncStatus = SyncStatus.P.name();

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
}
