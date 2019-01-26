package com.sonu.diary.util;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RestUtil {

    public static String sendPostRequest(String url, Object payload) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpClient = new DefaultHttpClient();//HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(payload);

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder res = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null){
            res.append(line);
        }
        in.close();
        return res.toString();
    }
}
