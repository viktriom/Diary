package com.sonu.diary.util;

import android.content.Context;
import android.os.Environment;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.Diary;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.domain.EntryEvent;
import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.domain.User;
import com.sonu.diary.domain.enums.SyncStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class DBUtil {

    private static QueryBuilder<User, String>  USER_QUERY_BUILDER;
    private static QueryBuilder<Diary, Integer> DIARY_QUERY_BUILDER;
    private static QueryBuilder<DiaryPage, Long> DIARY_PAGE_QUERY_BUILDER;
    private static QueryBuilder<DiaryEntry, Long> DIARY_ENTRY_QUERY_BUILDER;
    private static QueryBuilder<PaymentMode, String> PAYMENT_MODE_QUERY_BUILDER;
    private static QueryBuilder<EntryTitle, String> ENTRY_TITLE_QUERY_BUILDER;
    private static QueryBuilder<EntryEvent, String> ENTRY_EVENT_QUERY_BUILDER;

    static {
        try {
            USER_QUERY_BUILDER = (QueryBuilder<User, String>) DatabaseManager.getInstance().getHelper().getDao(User.class).queryBuilder();
            DIARY_QUERY_BUILDER = (QueryBuilder<Diary, Integer>) DatabaseManager.getInstance().getHelper().getDao(Diary.class).queryBuilder();
            DIARY_PAGE_QUERY_BUILDER = (QueryBuilder<DiaryPage, Long>) DatabaseManager.getInstance().getHelper().getDao(DiaryPage.class).queryBuilder();
            DIARY_ENTRY_QUERY_BUILDER = (QueryBuilder<DiaryEntry, Long>) DatabaseManager.getInstance().getHelper().getDao(DiaryEntry.class).queryBuilder();
            PAYMENT_MODE_QUERY_BUILDER = (QueryBuilder<PaymentMode, String>) DatabaseManager.getInstance().getHelper().getDao(PaymentMode.class).queryBuilder();
            ENTRY_TITLE_QUERY_BUILDER = (QueryBuilder<EntryTitle, String>) DatabaseManager.getInstance().getHelper().getDao(EntryTitle.class).queryBuilder();
            ENTRY_EVENT_QUERY_BUILDER = (QueryBuilder<EntryEvent, String>) DatabaseManager.getInstance().getHelper().getDao(EntryEvent.class).queryBuilder();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<DiaryPage> getPagesForMonth(int year, int month) throws SQLException {
        long idLow = (month*10000+year);
        long idHigh = (32*100+month)*10000+year;
        DIARY_PAGE_QUERY_BUILDER.reset();
        Where where = DIARY_PAGE_QUERY_BUILDER.where();
        where.between("diaryapage_id", idLow, idHigh);
        DIARY_PAGE_QUERY_BUILDER.setWhere(where);
        return DIARY_PAGE_QUERY_BUILDER.query();
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
        DIARY_PAGE_QUERY_BUILDER.reset();
        Where<DiaryPage, Long> where = DIARY_PAGE_QUERY_BUILDER.where();
        where.eq("diaryapage_id", pageId);
        DIARY_PAGE_QUERY_BUILDER.setWhere(where);
        return DIARY_PAGE_QUERY_BUILDER.queryForFirst();
    }

    public static Diary getDiaryForCurrentYear() throws SQLException {
        DIARY_QUERY_BUILDER.reset();
        Where<Diary, Integer> where = DIARY_QUERY_BUILDER.where();
        where.eq("diary_id", DateUtils.getCurrentYear());
        DIARY_QUERY_BUILDER.setWhere(where);
        return DIARY_QUERY_BUILDER.queryForFirst();
    }

    public static DiaryEntry getDiaryEntryForId(long id) throws SQLException {
        QueryBuilder<DiaryEntry, Long> queryBuilder = getQueryBuilderForDiaryEntry();
        Where<DiaryEntry, Long> where = queryBuilder.where();
        where.eq("diaryentry_id", id);
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

    public static List<User> getUsers(String role) throws SQLException {
        USER_QUERY_BUILDER.reset();
        Where<User, String> where = USER_QUERY_BUILDER.where();
        where.eq("role", role);
        USER_QUERY_BUILDER.setWhere(where);
        return USER_QUERY_BUILDER.query();
    }

    public static List<User> getUsersPendingSync() throws SQLException {
        USER_QUERY_BUILDER.reset();
        Where<User, String> where = USER_QUERY_BUILDER.where();
        where.eq("syncStatus", "P").or().isNull("syncStatus");
        USER_QUERY_BUILDER.setWhere(where);
        return USER_QUERY_BUILDER.query();
    }

    public static List<Diary> getDiariesPendingSync() throws SQLException {
        DIARY_QUERY_BUILDER.reset();
        Where<Diary, Integer> where = DIARY_QUERY_BUILDER.where();
        where.eq("syncStatus", "P").or().isNull("syncStatus");
        DIARY_QUERY_BUILDER.setWhere(where);
        return DIARY_QUERY_BUILDER.query();
    }

    public static List<DiaryPage> getDiaryPagesPendingSync() throws SQLException {
        DIARY_PAGE_QUERY_BUILDER.reset();
        Where<DiaryPage, Long> where = DIARY_PAGE_QUERY_BUILDER.where();
        where.eq("syncStatus", "P").or().isNull("syncStatus");
        DIARY_PAGE_QUERY_BUILDER.setWhere(where);
        return DIARY_PAGE_QUERY_BUILDER.query();
    }

    public static List<DiaryEntry> getDiaryEntriesPendingSync() throws SQLException {
        DIARY_ENTRY_QUERY_BUILDER.reset();
        Where<DiaryEntry, Long> where = DIARY_ENTRY_QUERY_BUILDER.where();
        where.eq("syncStatus", "P").or().isNull("syncStatus");
        DIARY_ENTRY_QUERY_BUILDER.setWhere(where);
        return DIARY_ENTRY_QUERY_BUILDER.query();
    }

    public static User getDiaryOwner() throws SQLException {
        USER_QUERY_BUILDER.reset();
        Where<User, String> where = USER_QUERY_BUILDER.where();
        where.eq("role", "Owner");
        USER_QUERY_BUILDER.setWhere(where);
        return USER_QUERY_BUILDER.queryForFirst();
    }

    public static List<PaymentMode> getPaymentModes() throws SQLException {
        PAYMENT_MODE_QUERY_BUILDER.reset();
        PAYMENT_MODE_QUERY_BUILDER.orderBy("description", true);
        return PAYMENT_MODE_QUERY_BUILDER.query();
    }

    public static List<EntryTitle> getEntryTitles() throws SQLException {
        ENTRY_TITLE_QUERY_BUILDER.reset();
        ENTRY_TITLE_QUERY_BUILDER.orderBy("title", true);
        return ENTRY_TITLE_QUERY_BUILDER.query();
    }

    public static List<EntryEvent> getEntryEvents() throws SQLException {
        ENTRY_EVENT_QUERY_BUILDER.reset();
        ENTRY_EVENT_QUERY_BUILDER.orderBy("eventname", true);
        return ENTRY_EVENT_QUERY_BUILDER.query();
    }

    public static void backupDBFile() throws IOException {
        String dbFilePath = "/data/data/com.sonu.diary/databases/diary.db";
        FileInputStream in = new FileInputStream(new File(dbFilePath));
        String dbBackupFileName = Environment.getExternalStorageDirectory() + "/diary_data_backup.db";
        OutputStream out = new FileOutputStream(dbBackupFileName);
        byte[] buff = new byte[1024];
        int length;
        while ((length = in.read(buff)) > 0){
            out.write(buff,0, length);
        }

        out.flush();
        out.close();
        in.close();
    }

}
