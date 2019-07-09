package com.sonu.diary.remote;

import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.Groups;

import java.util.List;

public interface EntryDataReceived {
    void dataReceived(List<DiaryEntry> data);
}
