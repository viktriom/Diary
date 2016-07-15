package com.sonu.diary.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by sonu on 15/07/16.
 */
public class DateUtils {

    private static Calendar calendar = null;


    private static void init(){
        calendar = new GregorianCalendar();
        calendar.setTimeInMillis(new Date().getTime());
    }

    public static int getCurrentDayOfMonth() {
        init();
        return calendar.get(Calendar.DATE);
    }

    public static int getCurrentMonth() {
        init();
        return calendar.get(Calendar.MONTH);
    }

    public static int getCurrentYear() {
        init();
        return calendar.get(Calendar.YEAR);
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static String getStringDateFromTimestampInFormat(Timestamp ts, String strFormat){
        SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);
        return format.format(ts);
    }



    public static String getStringTimeFromTimestamp(Timestamp ts){
        return getStringDateFromTimestampInFormat(ts,"MM/dd/yyyy HH:mm:ss").split(" ")[1];
    }

    public static String getTimeComponentFromTimestamp(Timestamp ts,int index){
        String strDate = getStringDateFromTimestampInFormat(ts,"MM/dd/yyyy HH:mm:ss").split(" ")[1];
        return strDate.split(":")[index];
    }

    public static String getDateComponentFromTimestamp(Timestamp ts, int index){
        String strDate = getStringDateFromTimestampInFormat(ts,"MM/dd/yyyy HH:mm:ss").split(" ")[0];
        return strDate.split("/")[index];
    }

    public static String getDayOfMonthFromTimestamp(Timestamp ts){
        return getDateComponentFromTimestamp(ts,1);
    }

    public static String getYearFromTimestamp(Timestamp ts){
        return getDateComponentFromTimestamp(ts,2);
    }

    public static String getMonthFromTimestamp(Timestamp ts){
        return getDateComponentFromTimestamp(ts,0);
    }

    public static String getHoursFromTimestamp(Timestamp ts){
        return getTimeComponentFromTimestamp(ts,0);
    }

    public static String getMinutesFromTimestamp(Timestamp ts){
        return getTimeComponentFromTimestamp(ts,1);
    }

    public static String getSecondsFromTimestamp(Timestamp ts){
        return getTimeComponentFromTimestamp(ts,2);
    }

}
