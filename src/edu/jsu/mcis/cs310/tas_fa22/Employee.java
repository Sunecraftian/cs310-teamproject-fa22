package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDateTime;

public class Employee {
    private final Integer id;
    private final String firstname, middlename, lastname;
    private final LocalDateTime active;
    private final Badge badge;
    private final Department department;
    private final Shift shift;

    public Employee(int id, String firstname, String middlename, String lastname, LocalDateTime active, Badge badge, Department department, Shift shift) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;

    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDateTime getActive() {
        return active;
    }

    public Badge getBadge() {
        return badge;
    }

    public Department getDepartment() {
        return department;
    }

    public Shift getShift() {
        return shift;
    }
}
