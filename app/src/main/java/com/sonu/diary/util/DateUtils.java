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
    public static final String defaultPrintableDateFormat = "MM/dd/yyyy HH:mm:ss";


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
        return getStringDateFromTimestampInFormat(ts,defaultPrintableDateFormat).split(" ")[1];
    }

    private static String getTimeComponentFromTimestamp(Timestamp ts,int index){
        String strDate = getStringDateFromTimestampInFormat(ts,defaultPrintableDateFormat).split(" ")[1];
        return strDate.split(":")[index];
    }

    private static String getDateComponentFromTimestamp(Timestamp ts, int index){
        String strDate = getStringDateFromTimestampInFormat(ts,defaultPrintableDateFormat).split(" ")[0];
        return strDate.split("/")[index];
    }

    public static int getDayOfMonthFromTimestamp(Timestamp ts){
        return Integer.parseInt(getDateComponentFromTimestamp(ts, 1));
    }

    public static int getYearFromTimestamp(Timestamp ts){
        return Integer.parseInt(getDateComponentFromTimestamp(ts, 2));
    }

    public static int getMonthFromTimestamp(Timestamp ts){
        return Integer.parseInt(getDateComponentFromTimestamp(ts, 0));
    }

    public static int getHoursFromTimestamp(Timestamp ts){
        return Integer.parseInt(getTimeComponentFromTimestamp(ts, 0));
    }

    public static int getMinutesFromTimestamp(Timestamp ts){
        return Integer.parseInt(getTimeComponentFromTimestamp(ts, 1));
    }

    public static int getSecondsFromTimestamp(Timestamp ts){
        return Integer.parseInt(getTimeComponentFromTimestamp(ts, 2));
    }

}
