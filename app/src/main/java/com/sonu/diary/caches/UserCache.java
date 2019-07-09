package com.sonu.diary.caches;

import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.domain.User;
import com.sonu.diary.util.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserCache {

    private static Map<String, User> users = new HashMap<>();

    public List<User> getValues(){
        if(users.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(users.values());
    }

    public void loadCache() {
        try {
            List<User> lst = DBUtil.getAllUsers();
            lst.forEach(t -> {
                users.put(t.getUserId(), t);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String userId){
        if(users.isEmpty()){
            loadCache();
        }
        return users.get(userId);
    }

    public List<String> getKeys(){
        if(users.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(users.keySet());
    }
}
