package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by sonu on 11/07/16.
 */
public class ConfidentialDiaryEntry{
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private DiaryEntry diaryEntry;


    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }
}
