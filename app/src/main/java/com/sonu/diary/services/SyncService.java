package com.sonu.diary.services;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sonu.diary.database.DatabaseHelper;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.domain.requestwrapper.RMDiaryEntry;
import com.sonu.diary.util.DBUtil;
import com.sonu.diary.util.RestUtil;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class SyncService {

    public static void syncDiaryEntries(Context context){
        try {
            List<DiaryEntry> lst = DBUtil.getDiaryEntriesPendingSync().stream().map(DiaryEntry::clone).collect(Collectors.toList());
            if(lst.size() == 0)
                return;
            RMDiaryEntry rmDiaryEntry = new RMDiaryEntry(lst, "viktri_moksh");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(rmDiaryEntry);
            StringEntity entity = new StringEntity(json);
            RestUtil.post(context, "diaryEntry", entity, new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    try {
                        String str = new String(response, "UTF-8");
                        Gson gson = new Gson();
                        Set<Integer> set = gson.fromJson(str, Set.class);
                        updateSyncStatusInDB(prepareUpdateQuery("diary", "diary_id", set, false, SyncStatus.C));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
                    try {
                        String str = new String(response, "UTF-8");
                        Log.e("SERVER SYNC", "There was an error while contacting the server: " + str);
                        Log.e("SERVER SYNC", error.getStackTrace().toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (SQLException e) {
            Log.e("SERVER SYNC", "Sync could not complete because Sync Util was unable to read data from the server.");
            e.printStackTrace();
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            Log.e("SERVER SYNC", "Error encountered while converting input list to JSON.");
            e.printStackTrace();
        }
    }

    public static void syncPendingData(Context context){
        syncDiaryEntries(context);
    }

    public static String prepareUpdateQuery(String tableName, String colName, Iterable collection, boolean isString, SyncStatus status){
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableName);
        sb.append(" set syncStatus = \'");
        sb.append(status.name());
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
