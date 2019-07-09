package com.sonu.diary.remote;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sonu.diary.caches.CacheManager;

import com.sonu.diary.database.DatabaseHelper;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.DiaryEntry;
import com.sonu.diary.domain.DiaryPage;
import com.sonu.diary.domain.Groups;
import com.sonu.diary.domain.User;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.domain.requestwrapper.RMDiaryEntry;
import com.sonu.diary.util.DBUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class SyncService {

    private static final String LOG_TAG = "SERVER_SYNC";

    public static void syncDiaryEntries(Context context){
        try {
            List<DiaryEntry> lst = DBUtil.getDiaryEntriesPendingSync().stream().map(DiaryEntry::clone).collect(Collectors.toList());
            if(lst.size() == 0)
                return;
            User owner = CacheManager.getDiaryCache().getOwner();
            RMDiaryEntry rmDiaryEntry = new RMDiaryEntry(lst, "", owner.getUserId());

            String json = RestUtil.getDefaultJsonFormatter().toJson(rmDiaryEntry);
            StringEntity entity = new StringEntity(json);
            RestUtil.post(context, RestServices.ENTRIES.getRestUrl(), entity, new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    String str = new String(response, StandardCharsets.UTF_8);
                    Set<Integer> set = RestUtil.getDefaultJsonFormatter().fromJson(str, Set.class);
                    updateSyncStatusInDB(prepareUpdateQuery("diaryentry", "diaryentry_id", set, false, SyncStatus.C));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
                    RestUtil.handleFailure(statusCode, headers, response, error);
                }

            });
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Sync could not complete because Sync Util was unable to read data from the server.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Error encountered while converting input list to JSON.");
            e.printStackTrace();
        }
    }

    public static void getEntriesFromServer(EntryDataReceived callback){
        User owner = CacheManager.getDiaryCache().getOwner();
        if(null == owner){
            Log.e(LOG_TAG, "Error getting entries from the server,  the owner user is is null.");
            return;
        }
        String url = String.format("%s/%s", RestServices.ENTRIES.getRestUrl(), owner.getUserId());
        RestUtil.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String str = new String(response, StandardCharsets.UTF_8);
                List<DiaryEntry> entries = RestUtil.getDefaultJsonFormatter().fromJson(str, new TypeToken<List<DiaryEntry>>(){}.getType());
                for(DiaryEntry entry: entries){
                    DiaryPage diaryPage = CacheManager.getDiaryCache().getDiaryPageForPageId(entry.getPageId());
                    entry.setDiaryPage(diaryPage);
                    CacheManager.getDiaryCache().addOrUpdateEntry(entry, false);
                    String userId = entry.getCreatedById();
                    getUserFromServer(userId);
                }
                callback.dataReceived(entries);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
                RestUtil.handleFailure(statusCode, headers, response, error);
            }
        });
    }


    public static void getAllGroups(GroupDataReceived callback){
        User owner = CacheManager.getDiaryCache().getOwner();
        String url = String.format("%s/%s", RestServices.GET_ALL_GROUPS.getRestUrl(), owner.getUserId());
        RestUtil.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String str = new String(response, StandardCharsets.UTF_8);
                List<Groups> groups = RestUtil.getDefaultJsonFormatter().fromJson(str, new TypeToken<List<Groups>>(){}.getType());
                DatabaseManager.getInstance().getHelper().deleteDataFromTable("groups");
                try {
                    for (Groups group : groups) {
                        Dao<Groups, Integer> groupDao = DatabaseManager.getInstance().getHelper().getDao(Groups.class);
                        groupDao.create(group);
                    }
                    CacheManager.getGroupCache().loadCache();
                    callback.dataReceived(groups);
                }catch(SQLException ex){
                    Log.e("SERVER_SYNC", "Error encountered while saving groups.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
                RestUtil.handleFailure(statusCode, headers, response, error);
            }
        });
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

    public static void createUserOnServer(Context context, User user) {
        try {
            String json = RestUtil.getDefaultJsonFormatter().toJson(user);
            StringEntity entity= new StringEntity(json);
            RestUtil.post(context, RestServices.CREATE_USER.getRestUrl(), entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String response = new String(responseBody, StandardCharsets.UTF_8);
                        String userId = RestUtil.getDefaultJsonFormatter().fromJson(response, String.class);
                        User user = DBUtil.getUser(userId);
                        if (null == userId)
                            return;
                        user.setSyncStatus(SyncStatus.C.name());
                        List<User> users = new LinkedList<>();
                        updateSyncStatusInDB(prepareUpdateQuery("user", "user_id", users, false, SyncStatus.C));
                    }catch(SQLException ex){
                        Log.e(LOG_TAG, "Sync could not complete because Sync Util was unable to read data from the server.");
                        Log.e(LOG_TAG, ex.getLocalizedMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Error encountered trying to send user details to server.");
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }
    }

    public static void getUserFromServer(String userId){
        if (null != CacheManager.getUserCache().getUser(userId)) {
            Log.i(LOG_TAG, "User details present locally, will not be fetched from server.");
            return;
        }
        String url = String.format("%s/%s", RestServices.CREATE_USER.getRestUrl(), userId);
        RestUtil.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, StandardCharsets.UTF_8);
                    User user = RestUtil.getDefaultJsonFormatter().fromJson(response, User.class);
                    Dao<User, Integer> userDao = DatabaseManager.getInstance().getHelper().getDao(User.class);
                    userDao.create(user);
                }catch(SQLException ex){
                    Log.e(LOG_TAG, "Successfully received data from remote server but error encountered while database persistence.");
                    Log.e(LOG_TAG, ex.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                RestUtil.handleFailure(statusCode, headers, responseBody, error);
            }
        });
    }

    public static void sendRegistrationToServer(String token) {

    }

    public static void testEnc() {
        try {
            String data = RestUtil.getDefaultJsonFormatter().toJson("This is a encrypted message.");
            StringEntity se = new StringEntity(data);

            String url = "testEnc";
            RestUtil.post(null, url, se, new AsyncHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
