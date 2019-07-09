package com.sonu.diary.remote;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sonu.diary.util.AppConstants;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RestUtil {

    private static final String LOG_TAG = "SERVER_SYNC";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static Gson gson = null;

    public static Gson getDefaultJsonFormatter(){
        if( null == gson) {
            gson = new GsonBuilder().registerTypeAdapter(Date.class, UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();
        }
        return gson;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, StringEntity params, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), params, "application/json", responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return AppConstants.REMOTE_SERVICE_HOSTNAME + relativeUrl;
    }


    public static void handleFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
        if(null == response){
            Log.e("SERVER SYNC", "Unable to reach the remote service. Status code : " + statusCode);
            return;
        }
        String str = new String(response, StandardCharsets.UTF_8);
        Log.e(LOG_TAG, "There was an error while contacting the server: " + str);
        Log.e(LOG_TAG, error.getLocalizedMessage());
    }
}