package com.sonu.diary.caches;

import com.sonu.diary.domain.EntryEvent;
import com.sonu.diary.domain.PaymentMode;
import com.sonu.diary.util.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntryEventCache {
    private static Map<String, EntryEvent> entryEvents = new HashMap<>();

    public List<EntryEvent> getEntryEvents(){
        if(entryEvents.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(entryEvents.values());
    }

    public void loadCache() {
        try {
            List<EntryEvent> lst = DBUtil.getEntryEvents();
            lst.forEach(t -> {
                entryEvents.put(t.getEventName(), t);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getEventNames(){
        if(entryEvents.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(entryEvents.keySet());
    }
}
