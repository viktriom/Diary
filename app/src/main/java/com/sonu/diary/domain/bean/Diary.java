package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by sonu on 11/07/16.
 */

@DatabaseTable
public class Diary {
    @DatabaseField(id = true)
    private int year;
    @DatabaseField
    private Person owner;
    @DatabaseField(foreign = true)
    private List<DiaryPage> diaryPages;

    public Diary(){

    }

    public Diary(int year, Person owner, List<DiaryPage> diaryPages) {
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

    public List<DiaryPage> getDiaryPages() {
        return diaryPages;
    }

    public void setDiaryPages(List<DiaryPage> diaryPages) {
        this.diaryPages = diaryPages;
    }
}
