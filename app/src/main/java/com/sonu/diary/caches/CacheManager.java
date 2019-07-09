package com.sonu.diary.caches;

/**
 * Created by sonu on 19/01/18.
 * This class is one single place which takes care of all the
 * caches used in the application.
 *
 */

public class CacheManager {

    private static DiaryCache diaryCache = null;

    private static EntryTitleCache entryTitleCache = null;

    private static PaymentModeCache paymentModeCache = null;

    private static EntryEventCache entryEventCache = null;

    private static GroupCache groupCache = null;

    private static UserCache userCache = null;

    public static DiaryCache getDiaryCache(){
        if(null == diaryCache){
            diaryCache = new DiaryCache();
        }
        return diaryCache;
    }

    public static EntryTitleCache getEntryTitleCache() {
        if(null == entryTitleCache){
            entryTitleCache = new EntryTitleCache();
        }
        return entryTitleCache;
    }

    public static PaymentModeCache getPaymentModeCache() {
        if(null == paymentModeCache){
            paymentModeCache = new PaymentModeCache();
        }
        return paymentModeCache;
    }

    public static EntryEventCache getEntryEventCache() {
        if ( null == entryEventCache){
            entryEventCache = new EntryEventCache();
        }
        return entryEventCache;
    }

    public static GroupCache getGroupCache() {
        if ( null == groupCache){
            groupCache = new GroupCache();
        }
        return groupCache;
    }

    public static UserCache getUserCache() {
        if( null == userCache){
            userCache = new UserCache();
        }
        return userCache;
    }

}
