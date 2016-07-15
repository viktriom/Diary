package com.sonu.diary.database;

import java.util.List;

/**
 * Created by sonu on 13/07/16.
 */
public interface DatabaseOperations {
    int create(Object item);
    int update(Object item);
    int delete(Object item);
    Object findById(int id);
    List<?> findAll();
}
