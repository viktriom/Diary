package com.sonu.diary.caches;

import android.content.Context;
import android.util.Log;

import com.sonu.diary.database.DatabaseOperations;
import com.sonu.diary.database.DatabaseOperationsImpl;
import com.sonu.diary.domain.bean.DiaryEntry;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sonu on 19/01/18.
 */

public class DiaryCache {

    private static List<DiaryEntry> diaryEntries = new LinkedList<>();

    public static void loadDiaryEntriesFromDb(Context context){
        if(diaryEntries.size() > 0) {
            Log.e(DiaryCache.class.getName(),"Diary entries already loaded.");
            return;
        }
        Log.e(DiaryCache.class.getName(),"Loading diary entries from DB now.");
        DatabaseOperations dbOpers = new DatabaseOperationsImpl(context);
        dbOpers.findAll(DiaryEntry.class).stream().peek(t -> diaryEntries.add((DiaryEntry)t));
    }

    public static void addNewDiaryEntry(DiaryEntry diaryEntry, Context context){
        DatabaseOperations dbOpers = new DatabaseOperationsImpl(context);
        dbOpers.create(DiaryEntry.class, diaryEntry);
        diaryEntries.add(diaryEntry);
    }

    public static DiaryEntry getDiaryEntry(int index){
        List<DiaryEntry> sortedList = diaryEntries.stream().sorted((t1, t2) -> (t1.getEntryActionTime().compareTo(t2.getEntryActionTime()))).collect(Collectors.toList());
        return sortedList.get(index);
    }

    public static List<DiaryEntry> getDiaryEntries() {
        return diaryEntries;
    }


}
