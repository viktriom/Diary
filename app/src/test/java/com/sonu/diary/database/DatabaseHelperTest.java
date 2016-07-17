package com.sonu.diary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.reflections.Reflections;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by sonu on 15/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseHelperTest {

    @Mock
    DatabaseHelper dbHelper;
    @Mock
    Context mMockContext;
    @Mock
    SQLiteDatabase db;
    @Mock
    Log log;
    @Mock
    SQLiteOpenHelper openHelper;

    DatabaseManager dbManager;

    @Before
    public void setUp() throws Exception {
        DatabaseManager.init(mMockContext);
        dbManager = DatabaseManager.getInstance();
        //dbHelper = dbManager.getHelper();
    }

    @Test
    public void testCreateTableForAllTheBeans() throws Exception {
        dbHelper.onCreate(db,dbHelper.getConnectionSource());
    }
}