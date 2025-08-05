package com.dealerapp.model;

public class Order {
    private int orderId, employeeId;
    private String customerCnp, status, orderDate;
    private double totalPrice;

    public Order(int orderId, int employeeId, String customerCnp, String status, String orderDate, double totalPrice) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.customerCnp = customerCnp;
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getCustomerCnp() {
        return customerCnp;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
