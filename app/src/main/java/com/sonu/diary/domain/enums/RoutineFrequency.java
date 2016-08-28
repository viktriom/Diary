package com.sonu.diary.domain.enums;

/**
 * Created by sonu on 27/08/16.
 */
public enum RoutineFrequency {
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3),
    SEMESTER(4),
    TRIMESTER(5),
    YEARLY(6);

    private int routineFrequency;


    RoutineFrequency(int routineFrequency){
        this.routineFrequency = routineFrequency;
    }

    public int getRoutineFrequency(){
        return routineFrequency;
    }
}
