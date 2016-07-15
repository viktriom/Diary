package com.sonu.diary.database;

import android.content.Context;

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
    public int create(Object item) {
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
    public Object findById(int id) {
        return null;
    }

    @Override
    public List<?> findAll() {
        return null;
    }
}
