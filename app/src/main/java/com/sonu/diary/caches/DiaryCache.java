package com.sonu.diary.caches;

import android.content.Context;

import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.bean.Diary;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.domain.bean.DiaryPage;
import com.sonu.diary.util.DBUtil;
import com.sonu.diary.util.DateUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by sonu on 19/01/18.
 */

public class DiaryCache {

    private Map<Long, DiaryPage> diaryPageCache = new HashMap<>();

    private Long currentPageId = DateUtils.getCurrentNumericDateForPageId();


    public void addOrUpdateEntry(DiaryEntry diaryEntry, Boolean editMode){
        Long pageId = diaryEntry.getDiaryPage().getPageId();
        if(!editMode){
            diaryPageCache.get(pageId).getDiaryEntry().add(diaryEntry);
        } else {
            try {
                DatabaseManager.getInstance().getHelper().getDao(DiaryEntry.class).update(diaryEntry);
                diaryPageCache.remove(pageId);
                populatePageCacheForDate(pageId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public DiaryEntry getDiaryEntry(int index){
        if(null == diaryPageCache.get(currentPageId) && null == diaryPageCache.get(currentPageId).getDiaryEntry()){
            return null;
        }
        List<DiaryEntry> sortedList = diaryPageCache.get(currentPageId).getDiaryEntry().stream().sorted((t1, t2) -> (t2.getEntryActionTime().compareTo(t1.getEntryActionTime()))).collect(Collectors.toList());
        return sortedList.get(index);
    }

    public List<DiaryEntry> getDiaryEntries() {
        List<DiaryEntry> de = new LinkedList<>();
        if(null != diaryPageCache.get(currentPageId) && null != diaryPageCache.get(currentPageId).getDiaryEntry())
            de.addAll(diaryPageCache.get(currentPageId).getDiaryEntry());
        return de;
    }

    public int getTotalExpenseForPage(){
        if(null == diaryPageCache.get(currentPageId)){
            populatePageForCurrentDate();
        }
        if(null == diaryPageCache.get(currentPageId) || null == diaryPageCache.get(DateUtils.getCurrentNumericDateForPageId()).getDiaryEntry()){
            return 0;
        }
        return diaryPageCache.get(currentPageId).getDiaryEntry().stream().filter(DiaryEntry::isExpenseAdded).map(DiaryEntry::getEntryExpenditure).reduce(0, (t1, t2)->t1+t2);
    }

    public void populatePageForCurrentDate(){
        populatePageCacheForDate(currentPageId);
    }

    public void populatePageCacheForDate(Long pageId){
        if(null == diaryPageCache.get(pageId)) {
            try {
                DiaryPage diaryPage = DBUtil.getDiaryPageForDate(pageId);
                if (null == diaryPage) {
                    Diary diary = DBUtil.getDiaryForCurrentYear();
                    diaryPage = new DiaryPage();
                    diaryPage.setPageId(pageId);
                    diaryPage.setPageDate(new Date(DateUtils.getCurrentTimestamp().getTime()));
                    diaryPage.setDiaryEntry(new LinkedList<>());
                    diaryPage.setDiary(diary);
                    diary.getDiaryPages().add(diaryPage);
                }
                diaryPageCache.put(diaryPage.getPageId(),diaryPage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeCaches(Context context){
        populatePageForCurrentDate();
    }

    public DiaryEntry getDiaryEntryForId(Long entryId){
        Timestamp ts = new Timestamp(entryId);
        DiaryEntry de = null;
        try {
            DiaryPage page = DBUtil.getDiaryPageForDate(DateUtils.getNumericDateForPageId(ts));
            diaryPageCache.put(page.getPageId(), page);
            de = page.getDiaryEntry().stream().filter(t -> t.getEntryId().equals(entryId)).findFirst().get();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return de;

    }

    public Long getCurrentPageId() {
        return currentPageId;
    }

    public void setCurrentPageId(Long currentPageId) {
        this.currentPageId = currentPageId;
        populatePageForCurrentDate();
    }

    public DiaryPage getDiaryPageForDate(Timestamp ts){
        Long pageId = DateUtils.getNumericDateForPageId(ts);
        populatePageCacheForDate(pageId);
        return diaryPageCache.get(pageId);
    }
}
