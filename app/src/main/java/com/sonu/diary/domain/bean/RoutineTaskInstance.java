package com.sonu.diary.domain.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sonu on 28/08/16.
 */
@DatabaseTable
public class RoutineTaskInstance{

    @DatabaseField(id = true, columnName = "routine_task_instance_id")
    private int routineTaskInstanceId;
    @DatabaseField
    private boolean routineTaskStatus;
    @DatabaseField
    private int rating;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private RoutineTask routineTask;

    public RoutineTaskInstance(){

    }

    public RoutineTaskInstance(int routineTaskInstanceId, boolean routineTaskStatus, int rating, RoutineTask routineTask) {
        this.routineTaskInstanceId = routineTaskInstanceId;
        this.routineTaskStatus = routineTaskStatus;
        this.rating = rating;
        this.routineTask = routineTask;
    }

    public int getRoutineTaskInstanceId() {
        return routineTaskInstanceId;
    }

    public void setRoutineTaskInstanceId(int routineTaskInstanceId) {
        this.routineTaskInstanceId = routineTaskInstanceId;
    }

    public boolean isRoutineTaskStatus() {
        return routineTaskStatus;
    }

    public void setRoutineTaskStatus(boolean routineTaskStatus) {
        this.routineTaskStatus = routineTaskStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public RoutineTask getRoutineTask() {
        return routineTask;
    }

    public void setRoutineTask(RoutineTask routineTask) {
        this.routineTask = routineTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutineTaskInstance)) return false;

        RoutineTaskInstance that = (RoutineTaskInstance) o;

        if (routineTaskInstanceId != that.routineTaskInstanceId) return false;
        if (routineTaskStatus != that.routineTaskStatus) return false;
        if (rating != that.rating) return false;
        return routineTask.equals(that.routineTask);

    }

    @Override
    public int hashCode() {
        int result = routineTaskInstanceId;
        result = 31 * result + (routineTaskStatus ? 1 : 0);
        result = 31 * result + rating;
        result = 31 * result + routineTask.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RoutineTaskInstance{" +
                "routineTaskInstanceId=" + routineTaskInstanceId +
                ", routineTaskStatus=" + routineTaskStatus +
                ", rating=" + rating +
                ", routineTask=" + routineTask +
                '}';
    }
}
