package com.sonu.diary.services;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sonu.diary.database.DatabaseHelper;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.Diary;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.domain.User;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.util.DBUtil;
import com.sonu.diary.util.RestUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SyncService {

    public static String SERVICE_URL = "http://192.168.1.3:8433/diaryserver/";

    public static void syncUsers(Context context){
        try {
            List<User> lst = DBUtil.getUsersPendingSync();
            if(null == lst || lst.size() == 0)
                return;
            String data = RestUtil.sendPostRequest(SERVICE_URL+"users", lst);
            Gson gson = new Gson();
            Set<String> set = gson.fromJson(data, Set.class);
            updateSyncStatusInDB(prepareUpdateQuery("user", "user_id", set, true));

        } catch (SQLException e) {
            Log.e("SERVER SYNC", "Sync could not complete because Sync Util was unable to read data from the server.");
        }
    }

    public static void syncDiaryDetails(Context context){
        try {
            List<Diary> lst = DBUtil.getDiariesPendingSync().stream().map(Diary::clone).collect(Collectors.toList());
            if(null == lst || lst.size() == 0)
                return;
            String data = RestUtil.sendPostRequest(SERVICE_URL+"diary", lst);
            Gson gson = new Gson();
            Set<Integer> set = gson.fromJson(data, Set.class);
            updateSyncStatusInDB(prepareUpdateQuery("diary", "diary_id", set, false));
        } catch (SQLException e) {
            Log.e("SERVER SYNC", "Sync could not complete because Sync Util was unable to read data from the server.");
        }
    }

    public static void syncDiaryPageDetails(Context context){
        try {
            List<DiaryPage> lst = DBUtil.getDiaryPagesPendingSync().stream().map(DiaryPage::clone).collect(Collectors.toList());
            if(null == lst || lst.size() == 0)
                return;
            String data = RestUtil.sendPostRequest(SERVICE_URL+"diaryPage", lst);
            Gson gson = new Gson();
            Set<Long> set = gson.fromJson(data, Set.class);
            updateSyncStatusInDB(prepareUpdateQuery("diaryPage", "diaryapage_id", set, false));
        } catch (SQLException e) {
            Log.e("SERVER SYNC", "Sync could not complete because Sync Util was unable to read data from the server.");
        }
    }

    public static void syncDiaryEntries(Context context){
        try {
            List<DiaryEntry> lst = DBUtil.getDiaryEntriesPendingSync().stream().map(DiaryEntry::clone).collect(Collectors.toList());
            if(null == lst || lst.size() == 0)
                return;
            String data = RestUtil.sendPostRequest(SERVICE_URL+"diaryEntry", lst);
            Gson gson = new Gson();
            Set<Long> set = gson.fromJson(data, Set.class);
            updateSyncStatusInDB(prepareUpdateQuery("diaryEntry", "diaryentry_id", set, false));
        } catch (SQLException e) {
            Log.e("SERVER SYNC", "Sync could not complete because Sync Util was unable to read data from the server.");
        }
    }

    public static void syncPendingData(Context context){
        new Thread() {
            public void run() {
                try {
                    syncUsers(context);
                    syncDiaryDetails(context);
                    syncDiaryPageDetails(context);
                    syncDiaryEntries(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static String prepareUpdateQuery(String tableName, String colName, Iterable collection, boolean isString){
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableName);
        sb.append(" set syncStatus = \'");
        sb.append(SyncStatus.C.name());
        sb.append("\' ");
        sb.append("where ");
        sb.append(colName);
        sb.append(" in (");
        if(isString) {
            sb.append("\"");
            sb.append(TextUtils.join("\",\"", collection));
            sb.append("\"");
        } else {
            sb.append(TextUtils.join(",", collection));
        }
        sb.append(")");
        return sb.toString();
    }

    public static void updateSyncStatusInDB(String query){
        DatabaseManager dbManager = DatabaseManager.getInstance();
        DatabaseHelper dbHelper = dbManager.getHelper();
        dbHelper.updateSyncStatus(query);
    }

}
