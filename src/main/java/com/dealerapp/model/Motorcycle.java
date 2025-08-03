package com.dealerapp.model;

public class Motorcycle extends Vehicle {
    private int engineCapacity;
    private boolean hasABS, is_A2_compatible;
    private String bikeType;

    public Motorcycle(int id, String make, String model, String engine, int year, double price, int mileage,
            int power, int torque, String bikeType, int engineCapacity, boolean hasABS, boolean is_A2_compatible) {
        super(id, make, model, engine, year, price, mileage, power, torque, "MOTORCYCLE");
        this.bikeType = bikeType;
        this.engineCapacity = engineCapacity;
        this.hasABS = hasABS;
        this.is_A2_compatible = is_A2_compatible;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public boolean hasABS() {
        return hasABS;
    }

    public boolean isA2Compatible() {
        return is_A2_compatible;
    }

    public String getBikeType() {
        return bikeType;
    }
}
