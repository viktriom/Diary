package com.sonu.diary.remote;

public enum RestServices {
    ENTRIES("entries"),
    GET_ALL_GROUPS("groups"),
    CREATE_USER("user");

    private String restUrl;

    RestServices(String restUrl){
        this.restUrl = restUrl;
    }

    public String getRestUrl(){
        return restUrl;
    }

}
