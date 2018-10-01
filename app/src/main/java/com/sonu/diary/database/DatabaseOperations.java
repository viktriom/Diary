package com.sonu.diary.database;

import java.util.List;

/**
 * Created by sonu on 13/07/16.
 */
public interface DatabaseOperations {
    int create(Class cls, Object item);
    int update(Object item);
    int delete(Object item);
    Object findById(Class cls, int id);
    Object findById(Class cls, long id);
    Object findById(Class cls, String id);
    List<?> findAll(Class cls);
}
