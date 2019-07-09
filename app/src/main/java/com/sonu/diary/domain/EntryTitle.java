package com.sonu.diary.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "entry_titles")
public class EntryTitle extends AbstractDomain {

    @DatabaseField
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getKey() {
        return title;
    }
}
