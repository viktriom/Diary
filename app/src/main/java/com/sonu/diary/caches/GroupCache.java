package com.sonu.diary.caches;


import com.sonu.diary.domain.Groups;
import com.sonu.diary.remote.GroupDataReceived;
import com.sonu.diary.remote.RestServices;
import com.sonu.diary.remote.SyncService;
import com.sonu.diary.util.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GroupCache {
    private Map<String, Groups> cache = new HashMap<>();

    public List<Groups> getGroups(){
        if(cache.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(cache.values());
    }

    public void refreshCacheFromRemoteService(GroupDataReceived callback){
        SyncService.getAllGroups(callback);
    }

    public void loadCache() {//jefri
        try {
            List<Groups> lst = DBUtil.getGroups();
            lst.forEach(t -> cache.put(t.getGroupId(), t));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getKeys(){
        if(cache.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(cache.keySet());
    }

}
