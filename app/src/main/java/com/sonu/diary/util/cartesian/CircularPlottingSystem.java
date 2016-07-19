package com.sonu.diary.util.cartesian;

import android.util.Log;

import com.sonu.diary.util.AppUtil;

/**
 * Created by sonu on 19/07/16.
 */
public class CircularPlottingSystem {
    private CartesianCoordinate origin;
    private int divisions;
    private double delta;
    private double radius;
    private int pointCount;
    private double arcStartAngle;
    private double arcEndAngle;


    public CircularPlottingSystem(CartesianCoordinate origin, double radius, int divisions, double arcStartAngle, double arcEndAngle){
        this.origin = origin;
        this.radius = radius;
        this.divisions = divisions;
        this.arcStartAngle = arcStartAngle;
        this.arcEndAngle = arcEndAngle;
        delta = Math.abs(this.arcEndAngle - this.arcStartAngle) / divisions;
    }

    public CartesianCoordinate getPointOnCircle(double angleInDegrees) {
        double x = (radius * Math.cos(angleInDegrees * Math.PI / 180d)) + origin.getX();
        double y = (radius * Math.sin(angleInDegrees * Math.PI / 180d)) + origin.getY();
        return new CartesianCoordinate(AppUtil.round(x,2), AppUtil.round(y,2));
    }

    public void reset(){
        this.pointCount = 0;
    }


    public CartesianCoordinate getNextPoint() {
        return getNthPoint(pointCount++);
    }

    public CartesianCoordinate getNthPoint(int i) {
        if(pointCount < 0 || pointCount > divisions){
            throw new IndexOutOfBoundsException("Point Count cannot be less than zero or greater than number of divisions requested.");
        }
        return getPointOnCircle(arcStartAngle + delta * i);
    }

    public CartesianCoordinate getAbsNthPoint(int i) {
        if(pointCount < 0 || pointCount > divisions){
            throw new IndexOutOfBoundsException("Point Count cannot be less than zero or greater than number of divisions requested.");
        }
        CartesianCoordinate cc = getPointOnCircle(arcStartAngle + delta * i);
        return new CartesianCoordinate(Math.abs(cc.getX()),Math.abs(cc.getY()));
    }
}
