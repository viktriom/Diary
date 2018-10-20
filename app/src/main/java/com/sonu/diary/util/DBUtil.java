package com.sonu.diary.util;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.bean.Diary;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.domain.bean.DiaryPage;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

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
        QueryBuilder<DiaryEntry, Long> queryBuilder = (QueryBuilder<DiaryEntry, Long>) DatabaseManager.getInstance().getHelper().getDao(DiaryEntry.class).queryBuilder();
        Where<DiaryEntry, Long> where = queryBuilder.where();
        where.eq("diaryentry_id", DateUtils.getCurrentYear());
        queryBuilder.setWhere(where);
        return queryBuilder.queryForFirst();
    }

}
