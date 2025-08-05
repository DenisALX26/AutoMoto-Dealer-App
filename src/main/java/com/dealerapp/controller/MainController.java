package com.dealerapp.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

import com.dealerapp.model.Car;
import com.dealerapp.model.Customer;
import com.dealerapp.model.Employee;
import com.dealerapp.model.Motorcycle;
import com.dealerapp.model.Order;
import com.dealerapp.model.Vehicle;
import com.dealerapp.db.DatabaseManager;

public class MainController {
    @FXML
    private TextField minYearField, maxYearField,
            minPriceField, maxPriceField,
            minMileageField, maxMileageField,
            minPowerField, maxPowerField,
            minTorqueField, maxTorqueField,
            vehicleIDField, vehiclePriceField,
            commissionField, totalPriceField,
            customerCNPField, customerFirstNameField, customerLastNameField, customerEmailField, customerPhoneField;
    @FXML
    private ComboBox<String> engineFilter, typeFilter;
    @FXML
    private Button showAllBtn, filterBtn, createOrderBtn, showEmployeesBtn, showCustomersBtn, showOrdersBtn;
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private ChoiceBox<String> employeesChoiceBox;
    @FXML
    private CheckBox newCustomerCheckBox;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableView<Order> orderTable;

    // Define the columns in the Vehicle TableView
    @FXML
    private TableColumn<Vehicle, Integer> idColumn, mileageColumn, yearColumn, powerColumn, torqueColumn;
    @FXML
    private TableColumn<Vehicle, String> makeColumn, modelColumn, engineColumn, typeColumn;
    @FXML
    private TableColumn<Vehicle, Double> priceColumn;

    // Define the column for Car specific attributes
    @FXML
    private TableColumn<Car, Integer> numberOfDoorsColumn;
    @FXML
    private TableColumn<Car, String> driveTypeColumn, colorColumn;

    // Define the column for Motorcycle specific attributes
    @FXML
    private TableColumn<Motorcycle, String> motorcycleTypeColumn;
    @FXML
    private TableColumn<Motorcycle, Integer> engineCapacityColumn;
    @FXML
    private TableColumn<Motorcycle, Boolean> hasABSColumn, isA2CompatibleColumn;

    // Define the columns for Employee TableView
    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Employee, String> firstNameColumn, lastNameColumn, hireDateColumn;
    @FXML
    private TableColumn<Employee, Double> commissionColumn;

    // Define the columns for Customer TableView
    @FXML
    private TableColumn<Customer, String> customerFirstNameColumn, customerLastNameColumn,
            customerEmailColumn, customerPhoneColumn, customerCnpColumn;

    // Define the columns for Order TableView
    @FXML
    private TableColumn<Order, Integer> orderIdColumn, employeeIdOrderColumn;
    @FXML
    private TableColumn<Order, String> customerCnpOrderColumn, statusColumn, orderDateColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    // Methods to hide specific columns based on vehicle type
    private void hideCarColumns() {
        numberOfDoorsColumn.setVisible(false);
        driveTypeColumn.setVisible(false);
        colorColumn.setVisible(false);
    }

    private void hideMotorcycleColumns() {
        motorcycleTypeColumn.setVisible(false);
        engineCapacityColumn.setVisible(false);
        hasABSColumn.setVisible(false);
        isA2CompatibleColumn.setVisible(false);
    }

    // Methods to hide the Vehicle, Employee, Customer and Order tables
    private void hideVehicleTable() {
        vehicleTable.setVisible(false);
        vehicleTable.setManaged(false);
    }

    private void hideEmployeeTable() {
        employeeTable.setVisible(false);
        employeeTable.setManaged(false);
    }

    private void hideCustomerTable() {
        customerTable.setVisible(false);
        customerTable.setManaged(false);
    }

    private void hideOrderTable() {
        orderTable.setVisible(false);
        orderTable.setManaged(false);
    }

    // Update the total price field based on the vehicle price and employee's
    // commission
    private void updateTotalPrice() {
        if (vehicleIDField.getText() != null && !vehicleIDField.getText().isEmpty()) {
            int vehicleID = Integer.parseInt(vehicleIDField.getText());
            double price = DatabaseManager.getVehiclePrice(vehicleID);
            vehiclePriceField.setText(String.valueOf(price));

            // Set the total price field based on the vehicle price and employee's
            // commission
            if (commissionField.getText() != null && !commissionField.getText().isEmpty()) {
                String commissionText = commissionField.getText().replace("%", "");
                double commission = Double.parseDouble(commissionText);
                double totalPrice = price + (price * (commission / 100));
                totalPriceField.setText(String.valueOf(totalPrice));
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        // Initialize the Vehicle TableView columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        engineColumn.setCellValueFactory(new PropertyValueFactory<>("engine"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        powerColumn.setCellValueFactory(new PropertyValueFactory<>("power"));
        torqueColumn.setCellValueFactory(new PropertyValueFactory<>("torque"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Initialize Car specific columns and hide them initially
        numberOfDoorsColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Car) {
                Car c = (Car) v;
                return new ReadOnlyIntegerWrapper(c.getNumberOfDoors()).asObject();
            } else {
                return new ReadOnlyIntegerWrapper(0).asObject(); // Default value if not a Car
            }
        });
        numberOfDoorsColumn.setVisible(false);

        driveTypeColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Car) {
                Car c = (Car) v;
                return new ReadOnlyStringWrapper(c.getDriveType());
            } else {
                return new ReadOnlyStringWrapper(""); // Default value if not a Car
            }
        });
        driveTypeColumn.setVisible(false);

        colorColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Car) {
                Car c = (Car) v;
                return new ReadOnlyStringWrapper(c.getColor());
            } else {
                return new ReadOnlyStringWrapper(""); // Default value if not a Car
            }
        });
        colorColumn.setVisible(false);

        // Initialize Motorcycle specific columns
        motorcycleTypeColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyStringWrapper(m.getBikeType());
            } else {
                return new ReadOnlyStringWrapper(""); // Default value if not a Motorcycle
            }
        });
        motorcycleTypeColumn.setVisible(false);

        engineCapacityColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyIntegerWrapper(m.getEngineCapacity()).asObject();
            } else {
                return new ReadOnlyIntegerWrapper(0).asObject(); // Default value if not a Motorcycle
            }
        });
        engineCapacityColumn.setVisible(false);

        hasABSColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyBooleanWrapper(m.hasABS()).asObject();
            } else {
                return new ReadOnlyBooleanWrapper(false).asObject(); // Default value if not a Motorcycle
            }
        });
        hasABSColumn.setVisible(false);

        isA2CompatibleColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyBooleanWrapper(m.isA2Compatible()).asObject();
            } else {
                return new ReadOnlyBooleanWrapper(false).asObject(); // Default value if not a Motorcycle
            }
        });
        isA2CompatibleColumn.setVisible(false);

        // Hide the table initially
        hideVehicleTable();

        // Initialize the Employee TableView columns
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        commissionColumn.setCellValueFactory(new PropertyValueFactory<>("commission"));

        // Hide the employee table initially
        hideEmployeeTable();

        // Initialize the Customer TableView columns
        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerCnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));

        // Hide the customer table initially
        hideCustomerTable();

        // Initialize the Order TableView columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        employeeIdOrderColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        customerCnpOrderColumn.setCellValueFactory(new PropertyValueFactory<>("customerCnp"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // Hide the order table initially
        hideOrderTable();

        // Set the options for the engine filter
        engineFilter.getItems().addAll("ANY", "PETROL", "DIESEL", "ELECTRIC", "HYBRID");

        // Set the options for the type filter
        typeFilter.getItems().addAll("ANY", "CAR", "MOTORCYCLE");

        // Set the behavior of the buttons
        showAllBtn.setOnAction(this::handleShowAll);
        filterBtn.setOnAction(this::handleFilter);
        createOrderBtn.setOnAction(this::handleCreateOrder);
        showEmployeesBtn.setOnAction(this::showEmployees);
        showCustomersBtn.setOnAction(this::showCustomers);
        showOrdersBtn.setOnAction(this::showOrders);

        // Load the employees into the ChoiceBox
        List<String> employees = DatabaseManager.getEmployeesNames();
        employeesChoiceBox.setItems(FXCollections.observableArrayList(employees));
        employeesChoiceBox.setValue("Select Employee");

        // Set the commission field with the choiced employee's commission
        employeesChoiceBox.setOnAction(event -> {
            String selectedEmployee = employeesChoiceBox.getValue();
            if (selectedEmployee != null && !selectedEmployee.isEmpty()) {
                int index = employees.indexOf(selectedEmployee);
                List<Double> commissions = DatabaseManager.getEmployeesCommissions();
                if (index >= 0 && index < commissions.size()) {
                    commissionField.setText(String.valueOf(commissions.get(index)) + "%");
                    // Update the total price field based on the selected employee's commission
                    updateTotalPrice();
                } else {
                    commissionField.setText("N/A");
                }
            } else {
                commissionField.setText("N/A");
            }
        });

        // Set the vehicle price field with the selected vehicle's id
        vehicleIDField.setOnAction(event -> {
            updateTotalPrice();
        });

        // Set the focus on the "Show All" button when the application starts
        Platform.runLater(() -> {
            showAllBtn.requestFocus();
        });
    }

    @FXML
    private void handleShowAll(ActionEvent event) {
        showAllVehicles();
    }

    @FXML
    private void handleFilter(ActionEvent event) {
        List<Vehicle> allVehicles = DatabaseManager.getAllVehicles();

        // Hide Tables
        hideEmployeeTable();
        hideCustomerTable();
        hideOrderTable();

        // Filter by vehicle type
        try {
            String selectedType = typeFilter.getValue();
            if (selectedType.equals("ANY") || selectedType == null || selectedType.isEmpty()) {
                // No filter applied, skip this vehicle type filter and hide Car specific
                // columns
                hideCarColumns();
                hideMotorcycleColumns();
            } else if (selectedType != null && !selectedType.isEmpty() && !selectedType.equals("ANY")) {
                allVehicles = allVehicles.stream()
                        .filter(v -> v.getType().equalsIgnoreCase(selectedType))
                        .collect(Collectors.toList());
                if (selectedType.equals("CAR")) {
                    // Show Car specific columns
                    numberOfDoorsColumn.setVisible(true);
                    driveTypeColumn.setVisible(true);
                    colorColumn.setVisible(true);

                    // Hide Motorcycle specific columns
                    hideMotorcycleColumns();
                } else if (selectedType.equals("MOTORCYCLE")) {
                    // Hide Car specific columns
                    hideCarColumns();

                    // Show Motorcycle specific columns
                    motorcycleTypeColumn.setVisible(true);
                    engineCapacityColumn.setVisible(true);
                    hasABSColumn.setVisible(true);
                    isA2CompatibleColumn.setVisible(true);
                }
            }
        } catch (Exception e) {
        }

        // Filter by engine type
        try {
            String selectedEngine = engineFilter.getValue();
            if (selectedEngine.equals("ANY") || selectedEngine == null || selectedEngine.isEmpty()) {
                // No filter applied, skip this engine type filter
            } else if (selectedEngine != null && !selectedEngine.isEmpty() && !selectedEngine.equals("ANY")) {
                allVehicles = allVehicles.stream()
                        .filter(v -> v.getEngine().equalsIgnoreCase(selectedEngine))
                        .collect(Collectors.toList());
            } else if (selectedEngine != null && !selectedEngine.isEmpty()) {
                allVehicles = allVehicles.stream()
                        .filter(v -> v.getEngine().equalsIgnoreCase(selectedEngine))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
        }

        // Filter by min year
        try {
            int minYear = Integer.parseInt(minYearField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getYear() >= minYear)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by max year
        try {
            int maxYear = Integer.parseInt(maxYearField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getYear() <= maxYear)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by min price
        try {
            double minPrice = Double.parseDouble(minPriceField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by max price
        try {
            double maxPrice = Double.parseDouble(maxPriceField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by min mileage
        try {
            int minMileage = Integer.parseInt(minMileageField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMileage() >= minMileage)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by max mileage
        try {
            int maxMileage = Integer.parseInt(maxMileageField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMileage() <= maxMileage)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by min power
        try {
            int minPower = Integer.parseInt(minPowerField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPower() >= minPower)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by max power
        try {
            int maxPower = Integer.parseInt(maxPowerField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPower() <= maxPower)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by min torque
        try {
            int minTorque = Integer.parseInt(minTorqueField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getTorque() >= minTorque)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Filter by max torque
        try {
            int maxTorque = Integer.parseInt(maxTorqueField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getTorque() <= maxTorque)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {

        }

        // Update the table with the filtered vehicles
        vehicleTable.setItems(FXCollections.observableArrayList(allVehicles));
        vehicleTable.setVisible(true);
        vehicleTable.setManaged(true);

    }

    @FXML
    private void handleCreateOrder(ActionEvent event) {
        if (vehicleIDField.getText().isEmpty() || customerCNPField.getText().isEmpty() ||
                employeesChoiceBox.getValue() == null || employeesChoiceBox.getValue().isEmpty() ||
                vehiclePriceField.getText().isEmpty()) {
            // Show an error message if any required field is empty
            showError("Please fill in all required fields.");
            return;
        }
        boolean isNewCustomer = newCustomerCheckBox.isSelected();
        String cnp = customerCNPField.getText();
        int selectedEmployee = DatabaseManager.getEmployeeID(employeesChoiceBox.getValue());
        double price = Double.parseDouble(totalPriceField.getText());

        // Check if the customer is new or existing
        if (!isNewCustomer) {
            // Check if the customer exists in the database
            if (!DatabaseManager.checkCustomerExists(cnp)) {
                showError(cnp + " does not exist in the database. Please create a new customer.");
                return;
            }
            // Create order for existing customer
            DatabaseManager.createOrder(cnp, selectedEmployee, "2023-03-17", "PENDING", price);
        } else {
            // Check if the customer already exists
            if (DatabaseManager.checkCustomerExists(cnp)) {
                showError(cnp + " already exists in the database.");
                return;
            }
            try {
                // Create order for new customer
                DatabaseManager.createOrder(cnp, selectedEmployee, "2023-03-17", "PENDING", price);
                
                // Create new customer
                String firstName = customerFirstNameField.getText(),
                        lastName = customerLastNameField.getText(),
                        email = customerEmailField.getText(),
                        phone = customerPhoneField.getText();

                DatabaseManager.createCustomer(cnp, firstName, lastName, email, phone);
            } catch (Exception e) {
                showError("Failed to create new customer: " + e.getMessage());
                return;
            }
        }
    }

    @FXML
    private void showEmployees(ActionEvent event) {
        employeeTable.setItems(FXCollections.observableArrayList(DatabaseManager.getEmployees()));
        employeeTable.setVisible(true);
        employeeTable.setManaged(true);

        // Hide Tables
        hideOrderTable();
        hideVehicleTable();
        hideCustomerTable();
    }

    @FXML
    private void showAllVehicles() {
        vehicleTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllVehicles()));
        vehicleTable.setVisible(true);
        vehicleTable.setManaged(true);

        // Hide Car and Motorcycle specific columns initially
        hideCarColumns();
        hideMotorcycleColumns();

        // Hide Tables
        hideEmployeeTable();
        hideCustomerTable();
        hideOrderTable();
    }

    @FXML
    private void showCustomers(ActionEvent event) {
        customerTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllCustomers()));
        customerTable.setVisible(true);
        customerTable.setManaged(true);

        // Hide Tables
        hideVehicleTable();
        hideEmployeeTable();
        hideOrderTable();
    }

    @FXML
    private void showOrders(ActionEvent event) {
        orderTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllOrders()));
        orderTable.setVisible(true);
        orderTable.setManaged(true);

        // Hide Tables
        hideVehicleTable();
        hideEmployeeTable();
        hideCustomerTable();
    }
}
