package com.sonu.diary.services;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpRequestTask<T> extends AsyncTask<Void, Void, T>{


    private HttpResponseCallback callback;
    private final String url;
    private Class specificType;

    public HttpRequestTask(HttpResponseCallback callback, String url, Class specificType){
        this.callback = callback;
        this.url = url;
        this.specificType = specificType;
    }

    @Override
    protected T doInBackground(Void... voids) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<T> result = restTemplate.getForEntity(url, specificType);
            return result.getBody();
        } catch (Exception e) {
            Log.e("HttpRequestTask", e.getMessage(), e);
        }
        return null;
    }



    @Override
    protected void onPostExecute(T questions){
        callback.handleResponse(questions);
    }
}
