package com.sonu.diary.domain.requestwrapper;


import com.google.gson.Gson;
import com.sonu.diary.domain.DiaryEntry;

import java.util.List;
import java.util.Objects;

public class RMDiaryEntry {
    List<DiaryEntry> entries;
    String groupId;

    public RMDiaryEntry() {
    }

    public RMDiaryEntry(List<DiaryEntry> entries, String groupId) {
        this.entries = entries;
        this.groupId = groupId;
    }

    public List<DiaryEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DiaryEntry> entries) {
        this.entries = entries;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RMDiaryEntry that = (RMDiaryEntry) o;
        return Objects.equals(entries, that.entries) &&
                Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entries, groupId);
    }

    @Override
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
