package com.sonu.diary.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sonu.diary.domain.enums.SyncStatus;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by sonu on 11/07/16.
 */

@DatabaseTable(tableName = "diary")
public class Diary {
    @DatabaseField(id = true, columnName = "diary_id")
    private Integer year;
    @DatabaseField (foreign = true, foreignAutoRefresh = true)
    @Expose(serialize = false, deserialize = false)
    private User owner;
    @ForeignCollectionField (eager = true)
    @Expose(serialize = false, deserialize = false)
    private Collection<DiaryPage> diaryPages;
    @DatabaseField
    private String syncStatus = SyncStatus.P.name();
    private String userId;

    public Diary(){

    }

    public Diary(int year, User owner, Collection<DiaryPage> diaryPages) {
        this.year = year;
        this.owner = owner;
        this.diaryPages = diaryPages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<DiaryPage> getDiaryPages() {
        return diaryPages;
    }

    public void setDiaryPages(Collection<DiaryPage> diaryPages) {
        this.diaryPages = diaryPages;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diary diary = (Diary) o;
        return Objects.equals(year, diary.year) &&
                Objects.equals(owner, diary.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, owner);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public Diary clone(){
        Diary d = new Diary();
        d.setYear(this.getYear());
        d.setUserId(this.getOwner().getUserId());
        d.setSyncStatus(this.getSyncStatus());
        return d;
    }
}
