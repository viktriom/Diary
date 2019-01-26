package com.sonu.diary.services;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SyncServiceTest {

    @Test
    public void updateSyncStatusInDB() {
        List<String> lst = new LinkedList<>();
        lst.add("test1");
        lst.add("test2");
        lst.add("test3");
        String query = SyncService.prepareUpdateQuery("users", "user_id", lst, true);
        System.out.println(query);
    }
}