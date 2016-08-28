package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by sonu on 27/08/16.
 */
@DatabaseTable
public class RoutineTask {

    @DatabaseField(id = true, columnName = "routine_task_id")
    private int routineTaskId;
    @DatabaseField
    private String routineTitle;
    @DatabaseField
    private Date routineStartTime;
    @DatabaseField
    private Date routineEndTime;
    @DatabaseField
    private int frequency;
    @DatabaseField
    private String routineSchedule;

    public RoutineTask(){

    }

    public RoutineTask(int routineTaskId, String routineTitle, Date routineStartTime, Date routineEndTime, int frequency, String routineSchedule) {
        this.routineTaskId = routineTaskId;
        this.routineTitle = routineTitle;
        this.routineStartTime = routineStartTime;
        this.routineEndTime = routineEndTime;
        this.frequency = frequency;
        this.routineSchedule = routineSchedule;
    }

    public int getRoutineTaskId() {
        return routineTaskId;
    }

    public void setRoutineTaskId(int routineTaskId) {
        this.routineTaskId = routineTaskId;
    }

    public String getRoutineTitle() {
        return routineTitle;
    }

    public void setRoutineTitle(String routineTitle) {
        this.routineTitle = routineTitle;
    }

    public Date getRoutineStartTime() {
        return routineStartTime;
    }

    public void setRoutineStartTime(Date routineStartTime) {
        this.routineStartTime = routineStartTime;
    }

    public Date getRoutineEndTime() {
        return routineEndTime;
    }

    public void setRoutineEndTime(Date routineEndTime) {
        this.routineEndTime = routineEndTime;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getRoutineSchedule() {
        return routineSchedule;
    }

    public void setRoutineSchedule(String routineSchedule) {
        this.routineSchedule = routineSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutineTask)) return false;

        RoutineTask routineTask = (RoutineTask) o;

        if (routineTaskId != routineTask.routineTaskId) return false;
        if (frequency != routineTask.frequency) return false;
        if (routineTitle != null ? !routineTitle.equals(routineTask.routineTitle) : routineTask.routineTitle != null)
            return false;
        if (routineStartTime != null ? !routineStartTime.equals(routineTask.routineStartTime) : routineTask.routineStartTime != null)
            return false;
        if (routineEndTime != null ? !routineEndTime.equals(routineTask.routineEndTime) : routineTask.routineEndTime != null)
            return false;
        return routineSchedule != null ? routineSchedule.equals(routineTask.routineSchedule) : routineTask.routineSchedule == null;

    }

    @Override
    public int hashCode() {
        int result = routineTaskId;
        result = 31 * result + (routineTitle != null ? routineTitle.hashCode() : 0);
        result = 31 * result + (routineStartTime != null ? routineStartTime.hashCode() : 0);
        result = 31 * result + (routineEndTime != null ? routineEndTime.hashCode() : 0);
        result = 31 * result + frequency;
        result = 31 * result + (routineSchedule != null ? routineSchedule.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoutineTask{" +
                "routineTaskId=" + routineTaskId +
                ", routineTitle='" + routineTitle + '\'' +
                ", routineStartTime=" + routineStartTime +
                ", routineEndTime=" + routineEndTime +
                ", frequency=" + frequency +
                ", routineSchedule=" + routineSchedule +
                '}';
    }
}
