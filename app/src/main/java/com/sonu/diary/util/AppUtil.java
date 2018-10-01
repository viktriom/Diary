package com.sonu.diary.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sonu on 19/07/16.
 */
public class AppUtil {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String getLocalizedStringFromTimestamp(String strTimestamp) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        Date parsedDate = dateFormat.parse(strTimestamp);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);

        return sdf.format(timestamp);
    }

}
