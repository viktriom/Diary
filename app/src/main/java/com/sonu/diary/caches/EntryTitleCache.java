package com.sonu.diary.caches;

import com.sonu.diary.domain.EntryTitle;
import com.sonu.diary.util.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntryTitleCache {

    private static Map<String, EntryTitle> entryTitles = new HashMap<>();

    public List<EntryTitle> getEntryTitles(){
        if(entryTitles.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(entryTitles.values());
    }

    public void loadCache() {
        try {
            List<EntryTitle> lst = DBUtil.getEntryTitles();
            lst.forEach(t -> {
                entryTitles.put(t.getTitle(), t);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getEntryTitleNames(){
        if(entryTitles.isEmpty()){
            loadCache();
        }
        return new LinkedList<>(entryTitles.keySet());
    }

}
