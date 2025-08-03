package com.dealerapp.model;

public class Customer {
    private String firstName, lastName, email, phoneNumber, cnp;

    public Customer(String firstName, String lastName, String email, String phoneNumber, String cnp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cnp = cnp;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCnp() {
        return cnp;
    }
}
