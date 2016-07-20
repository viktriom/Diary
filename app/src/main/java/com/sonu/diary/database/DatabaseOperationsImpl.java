package com.sonu.diary.database;

import android.content.Context;

import com.sonu.diary.domain.bean.DiaryPage;
import com.sonu.diary.util.DateUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sonu on 13/07/16.
 */
public class DatabaseOperationsImpl implements DatabaseOperations {

    private DatabaseHelper dbHelper;

    public DatabaseOperationsImpl(Context context){
        DatabaseManager.init(context);
        dbHelper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Class cls, Object item) {

        try {
            dbHelper.getDao(cls).create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Object item) {
        return 0;
    }

    @Override
    public int delete(Object item) {
        return 0;
    }

    @Override
    public Object findById(Class cls, int id) {
        Object obj = null;
        try {
            obj =  dbHelper.getDao(cls).queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Object findById(Class cls, long id) {
        Object obj = null;
        try {
            obj =  DatabaseManager.getInstance().getHelper().getDao(cls).queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Object findById(Class cls, String id) {
        Object obj = null;
        try {
            obj =  DatabaseManager.getInstance().getHelper().getDao(cls).queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public List<?> findAll() {
        return null;
    }
}
