package com.sonu.diary.domain.bean;

import java.sql.Date;
import org.joda.time.DateTime;

/**
 * Created by sonu on 11/07/16.
 */
public class Person {
    private String initials;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private float age;

    public Person(){
    }

    public Person(String initials, String firstName, String lastName, String middleName, Date dateOfBirth) {
        this.initials = initials;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
    }

    private void computeAndSetAge(){
        age = 0.0f;
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
        computeAndSetAge();
    }

    public float getAge() {
        return age;
    }
}
