package com.sonu.diary.domain;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.util.DateUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Created by sonu on 11/07/16.
 */
@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(id = true,columnName = "user_id")
    private String  userId;
    @DatabaseField
    private String initials;
    @DatabaseField
    private String firstName;
    @DatabaseField
    private String lastName;
    @DatabaseField
    private String middleName;
    @DatabaseField (columnName = "group")
    private String group;
    @DatabaseField (columnName = "role")
    private String role;
    @DatabaseField
    private Date dateOfBirth;
    @DatabaseField
    private float age;
    @DatabaseField
    private String syncStatus = SyncStatus.P.name();

    public User(){
    }

    public User(String userId, String initials, String firstName, String lastName, String middleName, String group, String role, Date dateOfBirth) {
        this.userId = userId;
        this.initials = initials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.group = group;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.age = DateUtils.calculateAge(dateOfBirth);
    }

    public float getAge() {
        return age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Float.compare(user.age, age) == 0 &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(initials, user.initials) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(middleName, user.middleName) &&
                Objects.equals(group, user.group) &&
                Objects.equals(role, user.role) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(syncStatus, user.syncStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, initials, firstName, lastName, middleName, group, role, dateOfBirth, age, syncStatus);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
