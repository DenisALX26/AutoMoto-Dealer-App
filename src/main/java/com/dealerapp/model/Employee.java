package com.dealerapp.model;

public class Employee {
    private int id;
    private double commission;
    private String firstName, lastName, hireDate;

    public Employee(int id, String firstName, String lastName, double commission, String hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.commission = commission;
        this.hireDate = hireDate;
    }

    public int getId() {
        return id;
    }

    public double getCommission() {
        return commission;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHireDate() {
        return hireDate;
    }
}
