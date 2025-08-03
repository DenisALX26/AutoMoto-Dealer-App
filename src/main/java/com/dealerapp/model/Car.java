package com.dealerapp.model;

public class Car extends Vehicle {
    private int number_of_doors;
    private String drive_type, color;

    public Car(int id, String make, String model, String engine, int year, double price, int mileage, int power,
            int torque, String type, int number_of_doors, String drive_type, String color) {
        super(id, make, model, engine, year, price, mileage, power, torque, type);
        this.number_of_doors = number_of_doors;
        this.drive_type = drive_type;
        this.color = color;
    }

    public int getNumberOfDoors() {
        return number_of_doors;
    }

    public String getDriveType() {
        return drive_type;
    }

    public String getColor() {
        return color;
    }
}
