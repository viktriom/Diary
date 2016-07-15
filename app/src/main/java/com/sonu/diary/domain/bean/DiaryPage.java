package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable
public class DiaryPage {
    @ForeignCollectionField(eager = true)
    private Collection<DiaryEntry> diaryEntry;
    @DatabaseField
    private Date pageDate;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Diary diary;

    public DiaryPage(List<DiaryEntry> diaryEntry, Date pageDate) {
        this.diaryEntry = diaryEntry;
        this.pageDate = pageDate;
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
}
