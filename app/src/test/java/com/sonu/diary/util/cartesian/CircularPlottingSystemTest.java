package com.sonu.diary.util.cartesian;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sonu on 19/07/16.
 */
public class CircularPlottingSystemTest {

    CircularPlottingSystem ps;
    CartesianCoordinate[] cartesianCoordinates = {
            new CartesianCoordinate(3.0,0.0),
            new CartesianCoordinate(2.77,1.15),
            new CartesianCoordinate(2.12,2.12),
            new CartesianCoordinate(1.15,2.77),
            new CartesianCoordinate(0.0,3.0)
    };
    @Before
    public void setUp() throws Exception {
        ps = new CircularPlottingSystem(new CartesianCoordinate(0d,0d),3,4,90,180);
    }

    @Test
    public void testGetNextPoint() throws Exception {
        for(int i = 0; i<5; i++){
            CartesianCoordinate c = ps.getNextPoint();
            assertEquals(c, cartesianCoordinates[i]);
            System.out.println(c);
        }
    }

    @Test
    public void testGetNthPoint() throws Exception {
        for(int i = 0; i<5; i++){
            CartesianCoordinate c = ps.getNthPoint(i);
            //assertEquals(c, cartesianCoordinates[i]);
            System.out.println(c);
        }
    }

    @Test
    public void testGetAbsNthPoint() throws Exception {
        for(int i = 0; i<5; i++){
            CartesianCoordinate c = ps.getAbsNthPoint(i);
            //assertEquals(c, cartesianCoordinates[i]);
            System.out.println(c);
        }
    }
}