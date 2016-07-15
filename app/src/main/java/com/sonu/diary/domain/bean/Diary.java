package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

/**
 * Created by sonu on 11/07/16.
 */

@DatabaseTable
public class Diary {
    @DatabaseField(id = true)
    private int year;
    @DatabaseField (foreign = true)
    private Person owner;
    @ForeignCollectionField (eager = true)
    private Collection<DiaryPage> diaryPages;

    public Diary(){

    }

    public Diary(int year, Person owner, Collection<DiaryPage> diaryPages) {
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Collection<DiaryPage> getDiaryPages() {
        return diaryPages;
    }

    public void setDiaryPages(Collection<DiaryPage> diaryPages) {
        this.diaryPages = diaryPages;
    }
}
