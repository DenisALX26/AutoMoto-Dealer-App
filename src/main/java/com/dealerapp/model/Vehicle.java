package com.dealerapp.model;

public class Vehicle {
    protected int id;
    private int mileage, year, power, torque;
    private double price;
    private String make, model, engine, type;

    public Vehicle(int id, String make, String model, String engine, int year, double price, int mileage, int power,
            int torque, String type) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.engine = engine;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.power = power;
        this.torque = torque;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getEngine() {
        return engine;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public int getMileage() {
        return mileage;
    }

    public int getPower() {
        return power;
    }

    public int getTorque() {
        return torque;
    }

    public String getType() {
        return type;
    }
}
