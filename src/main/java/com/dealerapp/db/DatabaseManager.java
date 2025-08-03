package com.dealerapp.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dealerapp.model.Car;
import com.dealerapp.model.Employee;
import com.dealerapp.model.Motorcycle;
import com.dealerapp.model.Vehicle;

public class DatabaseManager {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "dealer_user";
    private static final String PASS = "dealerpass";

    public static List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT v.*, c.drive_type, c.color, c.number_of_doors, m.engine_capacity, m.has_abs, m.is_a2_compatible, m.motorcycle_type from vehicle v left join car c on v.id = c.id left join motorcycle m on v.id = m.id order by v.id";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getString("type").equals("CAR")) {
                    vehicles.add(new Car(
                            resultSet.getInt("id"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getString("engine"),
                            resultSet.getInt("year"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("mileage"),
                            resultSet.getInt("power"),
                            resultSet.getInt("torque"),
                            resultSet.getString("type"),
                            resultSet.getInt("number_of_doors"),
                            resultSet.getString("drive_type"),
                            resultSet.getString("color")));
                } else if (resultSet.getString("type").equals("MOTORCYCLE")) {
                    vehicles.add(new Motorcycle(
                            resultSet.getInt("id"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getString("engine"),
                            resultSet.getInt("year"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("mileage"),
                            resultSet.getInt("power"),
                            resultSet.getInt("torque"),
                            resultSet.getString("motorcycle_type"),
                            resultSet.getInt("engine_capacity"),
                            resultSet.getBoolean("has_abs"),
                            resultSet.getBoolean("is_a2_compatible")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        String query = "SELECT * FROM EMPLOYEE order by id";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDouble("commission"),
                        resultSet.getString("hire_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static List<Integer> getEmployeesCommissions() {
        List<Integer> commissions = new ArrayList<>();

        String query = "SELECT commission FROM EMPLOYEE WHERE job_title = 'SALES'";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                commissions.add(resultSet.getInt("commission"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commissions;
    }

    public static double getVehiclePrice(int vehicleID) {
        double price = 0.0;

        String query = "SELECT price FROM VEHICLE WHERE id = " + vehicleID;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                price = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static int getEmployeeID(String employeeName) {
        int employeeID = -1;

        String query = "SELECT id FROM EMPLOYEE WHERE first_name || ' ' || last_name = '" + employeeName + "'";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                employeeID = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeID;
    }

    public static void createOrder(int vehicleID, String cnp, int employeeID, String order_date, String status,
            double price) {
        String query = "INSERT INTO Order_Table (vehicle_id, customer_cnp, employee_id, order_date, order_status, total_amount) VALUES ("
                + vehicleID + ", '" + cnp + "', " + employeeID + ", TO_DATE('" + order_date + "', 'DD-MM-YYYY'), '"
                + status + "', " + price + ")";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createCustomer(String cnp, String firstName, String lastName, String email, String phone) {
        String query = "INSERT INTO CUSTOMER (cnp, first_name, last_name, email, phone) VALUES ('" + cnp + "', '"
                + firstName + "', '" + lastName + "', '" + email + "', '" + phone + "')";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT v.*, c.drive_type, c.color, c.number_of_doors from vehicle v join car c on v.id = c.id order by v.id")) {
            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("make"),
                        resultSet.getString("model"),
                        resultSet.getString("engine"),
                        resultSet.getInt("year"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("mileage"),
                        resultSet.getInt("power"),
                        resultSet.getInt("torque"),
                        resultSet.getString("type"),
                        resultSet.getInt("number_of_doors"),
                        resultSet.getString("drive_type"),
                        resultSet.getString("color"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static List<Vehicle> getAllMotorcycles() {
        List<Vehicle> motorcycles = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT v.*, m.bike_type, m.engine_capacity, m.has_abs, m.is_a2_compatible from vehicle v join motorcycle m on v.id = m.id order by v.id")) {
            while (resultSet.next()) {
                Vehicle motorcycle = new Motorcycle(
                        resultSet.getInt("id"),
                        resultSet.getString("make"),
                        resultSet.getString("model"),
                        resultSet.getString("engine"),
                        resultSet.getInt("year"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("mileage"),
                        resultSet.getInt("power"),
                        resultSet.getInt("torque"),
                        resultSet.getString("bike_type"),
                        resultSet.getInt("engine_capacity"),
                        resultSet.getBoolean("has_abs"),
                        resultSet.getBoolean("is_a2_compatible"));
                motorcycles.add(motorcycle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return motorcycles;
    }
}
