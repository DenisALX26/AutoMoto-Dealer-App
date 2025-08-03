package com.dealerapp.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import com.dealerapp.model.Motorcycle;
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
    private Button showAllBtn, filterBtn, createOrderBtn;
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private ChoiceBox<String> employeesChoiceBox;
    @FXML
    private CheckBox newCustomerCheckBox;

    // Define the columns in the TableView
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

    @FXML
    private void initialize() {
        // Initialize the TableView columns
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
        numberOfDoorsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfDoors"));
        numberOfDoorsColumn.setVisible(false);

        driveTypeColumn.setCellValueFactory(new PropertyValueFactory<>("driveType"));
        driveTypeColumn.setVisible(false);

        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorColumn.setVisible(false);

        // Initialize Motorcycle specific columns
        motorcycleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bikeType"));
        motorcycleTypeColumn.setVisible(false);

        engineCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        engineCapacityColumn.setVisible(false);

        hasABSColumn.setCellValueFactory(new PropertyValueFactory<>("hasABS"));
        hasABSColumn.setVisible(false);

        isA2CompatibleColumn.setCellValueFactory(new PropertyValueFactory<>("isA2Compatible"));
        isA2CompatibleColumn.setVisible(false);

        // Hide the table initially
        vehicleTable.setVisible(false);
        vehicleTable.setManaged(false);

        // Set the options for the engine filter
        engineFilter.getItems().addAll("ANY", "PETROL", "DIESEL", "ELECTRIC", "HYBRID");

        // Set the options for the type filter
        typeFilter.getItems().addAll("ANY", "CAR", "MOTORCYCLE");

        // Set the behavior of the buttons
        showAllBtn.setOnAction(this::handleShowAll);
        filterBtn.setOnAction(this::handleFilter);
        createOrderBtn.setOnAction(this::handleCreateOrder);

        // Load the employees into the ChoiceBox
        List<String> employees = DatabaseManager.getSalesEmployees();
        employeesChoiceBox.setItems(FXCollections.observableArrayList(employees));
        employeesChoiceBox.setValue("Select Employee");

        // Set the commission field with the choiced employee's commission
        employeesChoiceBox.setOnAction(event -> {
            String selectedEmployee = employeesChoiceBox.getValue();
            if (selectedEmployee != null && !selectedEmployee.isEmpty()) {
                int index = employees.indexOf(selectedEmployee);
                List<Integer> commissions = DatabaseManager.getEmployeesCommissions();
                if (index >= 0 && index < commissions.size()) {
                    commissionField.setText(String.valueOf(commissions.get(index)) + "%");
                } else {
                    commissionField.setText("N/A");
                }
            } else {
                commissionField.setText("N/A");
            }
        });

        // Set the vehicle price field with the selected vehicle's id
        vehicleIDField.setOnAction(event -> {
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

        // Filter by vehicle type
        try {
            String selectedType = typeFilter.getValue();
            if (selectedType.equals("ANY") || selectedType == null || selectedType.isEmpty()) {
                // No filter applied, skip this vehicle type filter
            } else if (selectedType != null && !selectedType.isEmpty() && !selectedType.equals("ANY")) {
                allVehicles = allVehicles.stream()
                        .filter(v -> v.getType().equalsIgnoreCase(selectedType))
                        .collect(Collectors.toList());
            } else if (selectedType != null && !selectedType.isEmpty()) {
                allVehicles = allVehicles.stream()
                        .filter(v -> v.getType().equalsIgnoreCase(selectedType))
                        .collect(Collectors.toList());
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
        boolean isNewCustomer = newCustomerCheckBox.isSelected();
        String cnp = customerCNPField.getText();
        int vehicleID = Integer.parseInt(vehicleIDField.getText()),
                selectedEmployee = DatabaseManager.getEmployeeID(employeesChoiceBox.getValue());
        double price = Double.parseDouble(vehiclePriceField.getText());

        // Check if the customer is new or existing
        if (!isNewCustomer) {
            // Create order for existing customer
            DatabaseManager.createOrder(vehicleID, cnp, selectedEmployee, "17-03-2023", "PENDING", price);
        } else {
            // Create new customer
            String firstName = customerFirstNameField.getText(),
                    lastName = customerLastNameField.getText(),
                    email = customerEmailField.getText(),
                    phone = customerPhoneField.getText();

            DatabaseManager.createCustomer(cnp, firstName, lastName, email, phone);

            // Create order for new customer
            DatabaseManager.createOrder(vehicleID, cnp, selectedEmployee, "17-03-2023", "PENDING", price);
        }
    }

    @FXML
    private void showAllVehicles() {
        vehicleTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllVehicles()));
        vehicleTable.setVisible(true);
        vehicleTable.setManaged(true);
    }
}
