package com.sonu.diary.util;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.bean.Diary;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.domain.bean.DiaryPage;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DBUtil {

    public static List<DiaryPage> getPagesForMonth(int year, int month) throws SQLException {
        long idLow = (month*10000+year);
        long idHigh = (32*100+month)*10000+year;
        QueryBuilder<DiaryPage, Long> queryBuilder = (QueryBuilder<DiaryPage, Long>) DatabaseManager.getInstance().getHelper().getDao(DiaryPage.class).queryBuilder();
        Where where = queryBuilder.where();
        where.between("diaryapage_id", idLow, idHigh);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }

    public static List<DiaryPage> getPagesForCurrentMonth() throws SQLException {
        int year = DateUtils.getCurrentYear();
        int month = DateUtils.getCurrentMonth();
        return getPagesForMonth(year, month);
    }

    public static DiaryPage getTodaysPage() throws SQLException {
        return getDiaryPageForDate(DateUtils.getCurrentNumericDateForPageId());
    }

    public static DiaryPage getDiaryPageForDate(Long pageId) throws SQLException {
        QueryBuilder<DiaryPage, Long> queryBuilder = (QueryBuilder<DiaryPage, Long>) DatabaseManager.getInstance().getHelper().getDao(DiaryPage.class).queryBuilder();
        Where<DiaryPage, Long> where = queryBuilder.where();
        where.eq("diaryapage_id", pageId);
        queryBuilder.setWhere(where);
        return queryBuilder.queryForFirst();
    }

    public static Diary getDiaryForCurrentYear() throws SQLException {
        QueryBuilder<Diary, Integer> queryBuilder = (QueryBuilder<Diary, Integer>) DatabaseManager.getInstance().getHelper().getDao(Diary.class).queryBuilder();
        Where<Diary, Integer> where = queryBuilder.where();
        where.eq("diary_id", DateUtils.getCurrentYear());
        queryBuilder.setWhere(where);
        return queryBuilder.queryForFirst();
    }

    public static DiaryEntry getDiaryEntryForId(long id) throws SQLException {
        QueryBuilder<DiaryEntry, Long> queryBuilder = getQueryBuilderForDiaryEntry();
        Where<DiaryEntry, Long> where = queryBuilder.where();
        where.eq("diaryentry_id", DateUtils.getCurrentYear());
        queryBuilder.setWhere(where);
        return queryBuilder.queryForFirst();
    }

    public static List<DiaryEntry> getDiaryEntryForFilter(Where<DiaryEntry, Long> filterCondition) throws SQLException {
        QueryBuilder<DiaryEntry, Long> queryBuilder = getQueryBuilderForDiaryEntry();
        queryBuilder.setWhere(filterCondition);
        queryBuilder.orderBy("diaryentry_id", true);
        return queryBuilder.query();
    }

    public static  QueryBuilder<DiaryEntry, Long> getQueryBuilderForDiaryEntry() {
        try {
            return (QueryBuilder<DiaryEntry, Long>) DatabaseManager.getInstance().getHelper().getDao(DiaryEntry.class).queryBuilder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
