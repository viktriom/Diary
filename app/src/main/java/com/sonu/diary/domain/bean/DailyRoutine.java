package com.sonu.diary.domain.bean;

import java.util.List;

/**
 * Created by sonu on 27/08/16.
 */
public class DailyRoutine {

    List<RoutineTask> routineTasks;

    public List<RoutineTask> getRoutineTasks() {
        return routineTasks;
    }

    public void setRoutineTasks(List<RoutineTask> routineTasks) {
        this.routineTasks = routineTasks;
    }
}
