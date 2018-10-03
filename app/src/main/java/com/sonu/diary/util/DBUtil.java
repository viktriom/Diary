package com.sonu.diary.util;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.bean.DiaryPage;
import java.sql.SQLException;
import java.util.List;

public class DBUtil {

    public List<DiaryPage> getPagesForMonth(int year, int month) throws SQLException {
        long idLow = (month*10000+year);
        long idHigh = (32*100+month)*10000+year;
        QueryBuilder<DiaryPage, Long> queryBuilder = DatabaseManager.getInstance().getHelper().getDao(DiaryPage.class);
        Where where = queryBuilder.where();
        where.between("diaryapage_id", idLow, idHigh);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }


    public List<DiaryPage> getPagesForCurrentMonth() throws SQLException {
        int year = DateUtils.getCurrentYear();
        int month = DateUtils.getCurrentMonth();
        return getPagesForMonth(year, month);
    }

    public DiaryPage getTodaysPage(int year, int month, int dayOfMonth) throws SQLException {
        long pageId = (dayOfMonth*100+month)*10000+year;
        QueryBuilder<DiaryPage, Long> queryBuilder = DatabaseManager.getInstance().getHelper().getDao(DiaryPage.class);
        Where where = queryBuilder.where();
        where.eq("diaryapage_id", pageId);
        queryBuilder.setWhere(where);
        return queryBuilder.queryForFirst();
    }

}
