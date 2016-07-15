package com.sonu.diary.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by sonu on 15/07/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {

    Timestamp ts = null;

    @Before
    public void setUp() throws Exception {
        ts = new Timestamp(1468569315793l);
    }

    @Test
    public void testGetCurrentDayOfMonth() throws Exception {
        System.out.println(ts);
    }

    @Test
    public void testGetCurrentMonth() throws Exception {

    }

    @Test
    public void testGetCurrentYear() throws Exception {

    }

    @Test
    public void testGetCurrentTimestamp() throws Exception {

    }

    @Test
    public void testGetStringDateFromTimestampInFormat() throws Exception {
        System.out.println("testGetStringDateFromTimestampInFormat:"+DateUtils.getStringDateFromTimestampInFormat(ts,DateUtils.defaultPrintableDateFormat));
        assertEquals(DateUtils.getStringDateFromTimestampInFormat(ts, DateUtils.defaultPrintableDateFormat), "07/15/2016 13:25:15");
    }

    @Test
    public void testGetStringTimeFromTimestamp() throws Exception {
        System.out.println("testGetStringTimeFromTimestamp:"+DateUtils.getStringTimeFromTimestamp(ts));
        assertEquals(DateUtils.getStringTimeFromTimestamp(ts), "13:25:15");
    }

    @Test
    public void testGetDayOfMonthFromTimestamp() throws Exception {
        System.out.println("testGetDayOfMonthFromTimestamp:"+DateUtils.getDayOfMonthFromTimestamp(ts));
        assertEquals(DateUtils.getDayOfMonthFromTimestamp(ts), 15);
    }

    @Test
    public void testGetYearFromTimestamp() throws Exception {
        System.out.println("testGetYearFromTimestamp:"+DateUtils.getYearFromTimestamp(ts));
        assertEquals(DateUtils.getYearFromTimestamp(ts), 2016);
    }

    @Test
    public void testGetMonthFromTimestamp() throws Exception {
        System.out.println("testGetMonthFromTimestamp:"+DateUtils.getMonthFromTimestamp(ts));
        assertEquals(DateUtils.getMonthFromTimestamp(ts), 07);
    }

    @Test
    public void testGetHoursFromTimestamp() throws Exception {
        System.out.println("testGetHoursFromTimestamp:" + DateUtils.getHoursFromTimestamp(ts));
        assertEquals(DateUtils.getHoursFromTimestamp(ts), 13);
    }

    @Test
    public void testGetMinutesFromTimestamp() throws Exception {
        System.out.println("testGetMinutesFromTimestamp:"+DateUtils.getMinutesFromTimestamp(ts));
        assertEquals(DateUtils.getMinutesFromTimestamp(ts), 25);
    }

    @Test
    public void testGetSecondsFromTimestamp() throws Exception {
        System.out.println("testGetSecondsFromTimestamp:"+DateUtils.getSecondsFromTimestamp(ts));
        assertEquals(DateUtils.getSecondsFromTimestamp(ts), 15);
    }
}