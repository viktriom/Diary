package com.sonu.diary.caches;

import android.content.Context;

import com.j256.ormlite.stmt.Where;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.Diary;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.services.SyncService;
import com.sonu.diary.util.DBUtil;
import com.sonu.diary.util.DateUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sonu on 19/01/18.
 */

public class DiaryCache {

    private Map<Long, DiaryPage> diaryPageCache = new HashMap<>();

    private Long currentPageId = DateUtils.getCurrentNumericDateForPageId();

    private boolean isFilterApplied = false;

    private Where<DiaryEntry, Long> filterCondition = DBUtil.getQueryBuilderForDiaryEntry().where();


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
        SyncService.syncPendingData(null);
    }

    public DiaryEntry getDiaryEntry(int index){
        if(null == diaryPageCache.get(currentPageId) && null == diaryPageCache.get(currentPageId).getDiaryEntry()){
            return null;
        }
        List<DiaryEntry> sortedList = null;
        if(isFilterApplied) {
            sortedList = diaryPageCache.get(0L).getDiaryEntry().stream().sorted((t1, t2) -> (t2.getEntryActionTime().compareTo(t1.getEntryActionTime()))).collect(Collectors.toList());
        }else {
            sortedList = new LinkedList<>(diaryPageCache.get(currentPageId).getDiaryEntry());
        }
        return sortedList.get(index);
    }

    public List<DiaryEntry> getDiaryEntries() {
        List<DiaryEntry> de = new LinkedList<>();
        if(!isFilterApplied) {
            if (null != diaryPageCache.get(currentPageId) && null != diaryPageCache.get(currentPageId).getDiaryEntry())
                de.addAll(diaryPageCache.get(currentPageId).getDiaryEntry());
            return de;
        } else {
            return filteredEntries();
        }
    }

    private List<DiaryEntry> filteredEntries() {
        LinkedList<DiaryEntry> lst = new LinkedList<>();
        try {
            lst.addAll(DBUtil.getDiaryEntryForFilter(filterCondition));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!diaryPageCache.containsKey(0L)) {
            DiaryPage diaryPage = new DiaryPage();
            diaryPage.setPageId(0L);
            diaryPage.setDiaryEntry(lst);
            diaryPageCache.put(0L, diaryPage);
        }
        return new LinkedList<>(diaryPageCache.get(0L).getDiaryEntry());
    }

    public int getTotalExpenseForPage(){
        return getDiaryEntries().stream().filter(DiaryEntry::isExpenseAdded).map(DiaryEntry::getEntryExpenditure).reduce(0, (t1, t2)->t1+t2);
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

    public void clearFilters(){
        isFilterApplied = false;
        filterCondition.reset();
        diaryPageCache.remove(0L);
    }

    public void applyFilter(){
        isFilterApplied=true;
    }

    public void addSearchStringToFilter(String searchString){
        try {
            filterCondition.like("entryTitle", searchString);
            filterCondition.or().like("entryDescription", searchString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDatesToFilterCondition(Timestamp fromDate, Timestamp toDate, boolean isSearchStringAdded){
        try {
            if(isSearchStringAdded)
                filterCondition.and();
            filterCondition.between("entryActionTime", fromDate, toDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFIlterApplied(){ return isFilterApplied; }

}
