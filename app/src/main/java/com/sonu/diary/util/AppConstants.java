package com.sonu.diary.util;

/**
 * Created by sonu on 20/04/18.
 */

public class AppConstants {
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

    //public static final String REMOTE_SERVICE_HOSTNAME="http://vakkr.com:8433/diaryserver/";

    //public static final String REMOTE_SERVICE_HOSTNAME="http://192.168.1.3:8433/diaryserver/";

    public static final String REMOTE_SERVICE_HOSTNAME="http://192.168.1.7:8433/diaryserver/";

    public static final String NOT_SHARED_INDICATOR = "None";

    public static final String OWNER_ROLE = "Owner";

    public static final String TITLE_KEY = "category";

    public static final String EVENT_NAME_KEY = "eventName";

}
