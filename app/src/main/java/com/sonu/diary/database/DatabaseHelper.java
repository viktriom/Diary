package com.sonu.diary.database;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.sonu.diary.domain.bean.Diary;
import com.sonu.diary.domain.bean.DiaryEntry;
import com.sonu.diary.domain.bean.DiaryPage;
import com.sonu.diary.domain.bean.Person;
import com.sonu.diary.util.DateUtils;

import static com.sonu.diary.util.DateUtils.*;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Diary, Integer> diaryDao = null;
    private Dao<Person, Integer> personDao = null;

    private Map<String, Dao<?,?>> daoMap = new HashMap<>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Class[] dbMappedClassList = {Diary.class, DiaryEntry.class, DiaryPage.class, Person.class };

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        Log.i(DatabaseHelper.class.getName(), "onCreate");
        createTableForAllTheBeans();
        createDiaryForCurrentYear();
    }

    public void createTableForAllTheBeans() {
        //TODO: Automate finding the list of beans for table creation.

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
            Dao<Person,Integer> personDao = getDao(Person.class);
            Dao<Diary, Integer> diaryDao = getDao(Diary.class);
            Dao<DiaryPage, Long> diaryPageDao = getDao(DiaryPage.class);
            long recCount = personDao.countOf();
            Person person = null;
            if(recCount == 0){
                person = personDao.queryForId(1);
                if(person == null) {
                    person = new Person(1,"Mr", "Vivek", "Tripathi", "", DateUtils.getDateFromString("02/07/1988"));
                    personDao.create(person);
                }
            }
            recCount = diaryDao.countOf();
            if(recCount == 0){
                 if(null == diaryDao.queryForId(DateUtils.getCurrentYear())) {
                     List<DiaryPage> diaryPageList = new LinkedList<>();
                     DiaryPage diaryPage = new DiaryPage();
                     Diary diary = new Diary();
                     diaryPage.setPageId(Long.parseLong(
                                     DateUtils.getStringDateFromTimestampInFormat(DateUtils.getCurrentTimestamp(),
                                             DateUtils.NUMERIC_DATE_FORMAT_WITHOUT_SEPARATORS))
                     );
                     diaryPage.setPageDate(new Date(DateUtils.getCurrentTimestamp().getTime()));
                     diaryPage.setDiaryEntry(null);
                     diary.setYear(getCurrentYear());
                     diary.setOwner(person);
                     diaryPage.setDiary(diary);
                     diaryPageList.add(diaryPage);
                     diary.setDiaryPages(diaryPageList);
                     diaryDao.create(diary);
                     diaryPageDao.create(diaryPage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(DatabaseHelper.class.getName(), "Unable to insert data into Person and Diary table during application initialization. SQL Sate:"
                    + e.getSQLState()
                    + "SQL Error Code:" + e.getErrorCode()
                    + "error Message: " + e.getMessage());
        }
        Log.i(DatabaseHelper.class.getName(),"Data SUCCESSFULLY inserted into Person and Diary table during application initialization.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Diary.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            //Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public Dao<?, ?> getRuntimeDao(Class cls) throws SQLException {
        Dao<?,?> dao = daoMap.get(cls.getName());
        if(null == dao){
            dao = DaoManager.createDao(connectionSource, cls);
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