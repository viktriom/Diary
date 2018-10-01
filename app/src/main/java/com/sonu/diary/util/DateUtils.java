package com.sonu.diary.util;

import org.joda.time.LocalDate;
import org.joda.time.Years;


import java.sql.Timestamp;
import java.text.ParseException;
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
    public static final String DEFAULT_TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    public static final String NUMERIC_DATE_FORMAT_WITHOUT_SEPARATORS = "ddMMyyyy";

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
        return getStringDateFromTimestampInFormat(ts, DEFAULT_TIMESTAMP_FORMAT).split(" ")[1];
    }

    private static String getTimeComponentFromTimestamp(Timestamp ts,int index){
        String strDate = getStringDateFromTimestampInFormat(ts, DEFAULT_TIMESTAMP_FORMAT).split(" ")[1];
        return strDate.split(":")[index];
    }

    private static String getDateComponentFromTimestamp(Timestamp ts, int index){
        String strDate = getStringDateFromTimestampInFormat(ts, DEFAULT_TIMESTAMP_FORMAT).split(" ")[0];
        return strDate.split("/")[index];
    }

    public static int getDayOfMonthFromTimestamp(Timestamp ts){
        return Integer.parseInt(getDateComponentFromTimestamp(ts, 0));
    }

    public static int getYearFromTimestamp(Timestamp ts){
        return Integer.parseInt(getDateComponentFromTimestamp(ts, 2));
    }

    public static int getMonthFromTimestamp(Timestamp ts){
        return Integer.parseInt(getDateComponentFromTimestamp(ts, 1));
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

    private static Date getDateFromStringInFormat(String strDate, String format){
        Date date = null;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            date = dateFormatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Timestamp getTimestampFromStringInFormat(String strDate, String format){
        Date date = null;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            date = dateFormatter.parse(strDate);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromString(String strDate){
        return getDateFromStringInFormat(strDate, DEFAULT_DATE_FORMAT);
    }

    public static Timestamp getTimestampFromString(String strDate){
        Date date = getDateFromString(strDate);
        return new Timestamp(date.getTime());
    }


    public static int calculateAge(Date birthDate) {
        LocalDate bDate = new LocalDate(birthDate);
        LocalDate now = new LocalDate(getCurrentTimestamp()); // test, in real world without args
        Years age = Years.yearsBetween(bDate, now);
        return age.getYears();
    }


}
