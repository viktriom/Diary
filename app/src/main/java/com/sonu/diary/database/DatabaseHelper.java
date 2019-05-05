package com.sonu.diary.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.sonu.diary.domain.Diary;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.domain.EntryEvent;
import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.domain.User;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.util.DBUtil;
import com.sonu.diary.util.DateUtils;

import static com.sonu.diary.util.DateUtils.*;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<Diary, Integer> diaryDao = null;
    private Dao<User, Integer> personDao = null;

    private Map<String, Dao<?,?>> daoMap = new HashMap<>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Class[] dbMappedClassList = {Diary.class, DiaryEntry.class, DiaryPage.class, User.class, EntryTitle.class, PaymentMode.class, EntryEvent.class};

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        Log.i(DatabaseHelper.class.getName(), "onCreate");
        createTableForAllTheBeans();
        createDiaryForCurrentYear();
    }

    public void createTableForAllTheBeans() {
        for(Class cls : dbMappedClassList){
            if(cls.isAnnotationPresent(DatabaseTable.class)) {
                System.out.println("Creating table for the bean: " + cls);
                try {
                    TableUtils.createTableIfNotExists(connectionSource, cls);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e(DatabaseHelper.class.getName(), "Table creation for bean " + cls + "UNSUCCESSFUL due to error : " + e.getMessage());
                }
            }
        }
    }

    public void updateSyncStatusToP() {
        String updateSql = "update diary set syncStatus = 'P'";
        updateSyncStatus(updateSql);
        updateSql = "update diaryPage set syncStatus = 'P'";
        updateSyncStatus(updateSql);
        updateSql = "update diaryentry set syncStatus = 'P'";
        updateSyncStatus(updateSql);
        updateSql = "update user set syncStatus = 'P'";
        updateSyncStatus(updateSql);
    }

    public void dropAllTables(){
        for(Class cls : dbMappedClassList){
            if(cls.isAnnotationPresent(DatabaseTable.class)) {
                System.out.println("Creating table for the bean: " + cls);
                try {
                    TableUtils.dropTable(connectionSource, cls, true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e(DatabaseHelper.class.getName(), "Drop table for bean :  " + cls + " UNSUCCESSFUL due to error : " + e.getMessage());
                }
            }
        }
    }

    public void truncateAllTables(){
        for(Class cls : dbMappedClassList){
            if(cls.isAnnotationPresent(DatabaseTable.class)) {
                System.out.println("Truncating table for the bean: " + cls);
                try {
                    TableUtils.clearTable(connectionSource, cls);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e(DatabaseHelper.class.getName(), "Truncation of table for bean " + cls + "UNSUCCESSFUL due to error : " + e.getMessage());
                }
            }
        }
    }

    public void createDiaryForCurrentYear() {
        try {
            Dao<User,String> userDao = getDao(User.class);
            Dao<Diary, Integer> diaryDao = getDao(Diary.class);
            Dao<DiaryPage, Long> diaryPageDao = getDao(DiaryPage.class);
            List<User> users = DBUtil.getUsers("Owner");
            User user;
            if(null == users || users.size() == 0){
                    user = new User("viktri","Mr", "Vivek", "Tripathi", "", "tripathi", "Owner", DateUtils.getDateFromString("02/07/1988"));
                    userDao.create(user);
            } else {
                user = users.get(0);
            }

            Diary diary = diaryDao.queryForId(DateUtils.getCurrentYear());
            long diaryPageId = Long.parseLong(DateUtils.getStringDateFromTimestampInFormat(DateUtils.getCurrentTimestamp(), DateUtils.NUMERIC_DATE_FORMAT_WITHOUT_SEPARATORS));
            if(null == diary){
                     List<DiaryPage> diaryPageList = new LinkedList<>();
                     DiaryPage diaryPage = new DiaryPage();
                     diary = new Diary();
                     diaryPage.setPageId(diaryPageId);
                     diaryPage.setPageDate(new Date(DateUtils.getCurrentTimestamp().getTime()));
                     diaryPage.setDiaryEntry(null);
                     diary.setYear(getCurrentYear());
                     diary.setOwner(user);
                     diaryPage.setDiary(diary);
                     diaryPageList.add(diaryPage);
                     diary.setDiaryPages(diaryPageList);
                     diaryDao.create(diary);
            }

            if(null == diaryPageDao.queryForId(diaryPageId)){
                DiaryPage diaryPage = new DiaryPage();
                diaryPage.setPageId(diaryPageId);
                diaryPage.setPageDate(new Date(DateUtils.getCurrentTimestamp().getTime()));
                diaryPage.setDiaryEntry(null);
                diaryPage.setDiary(diary);
                diary.getDiaryPages().add(diaryPage);
                diaryPageDao.create(diaryPage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(DatabaseHelper.class.getName(), "Unable to insert data into User and Diary table during application initialization. SQL Sate:"
                    + e.getSQLState()
                    + "SQL Error Code:" + e.getErrorCode()
                    + "error Message: " + e.getMessage());
        }
        Log.i(DatabaseHelper.class.getName(),"Data SUCCESSFULLY inserted into User and Diary table during application initialization.");
    }

    public void updateSyncStatus(String query){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(DatabaseHelper.class.getName(), "Upgrading DB from version : " + oldVersion + " to : " + newVersion);
    }


    public Dao<?, ?> getRuntimeDao(Class cls) throws SQLException {
        Dao<?,?> dao = daoMap.get(cls.getName());
        if(null == dao){
            dao = (Dao<?, ?>)DaoManager.createDao(connectionSource, cls);
        }
        daoMap.put(cls.getName(), dao);
        return dao;
    }

    @Override
    public void close() {
        super.close();
        diaryDao = null;
        daoMap.clear();
    }
}