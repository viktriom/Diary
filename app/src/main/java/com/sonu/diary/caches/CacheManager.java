package com.sonu.diary.caches;

/**
 * Created by sonu on 19/01/18.
 * This class is one single place which takes care of all the
 * caches used in the application.
 *
 */

public class CacheManager {

    private static DiaryCache diaryCache = null;

    public static DiaryCache getDiaryCache(){
        if(null == diaryCache){
            diaryCache = new DiaryCache();
        }
        return diaryCache;
    }

}
