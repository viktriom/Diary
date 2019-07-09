package com.sonu.diary.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@DatabaseTable
public class Groups extends AbstractDomain {

    @DatabaseField
    private String groupId;

    @DatabaseField
    private String groupDescription;

    @DatabaseField(columnName = "createdBy")
    private String createdById;

    private User createdBy;

    @DatabaseField
    private Timestamp createdTime;

    private List<User> users;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdById = createdBy.getUserId();
        this.createdBy = createdBy;
    }

    @Override
    public String getKey() {
        return groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return Objects.equals(groupId, groups.groupId) &&
                Objects.equals(groupDescription, groups.groupDescription) &&
                Objects.equals(createdById, groups.createdById) &&
                Objects.equals(createdTime, groups.createdTime) &&
                Objects.equals(users, groups.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupDescription, createdById, createdTime, users);
    }
}
