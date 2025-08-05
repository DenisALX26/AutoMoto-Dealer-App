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

    @FXML
    private TableColumn<Vehicle, Integer> idColumn, mileageColumn, yearColumn, powerColumn, torqueColumn;
    @FXML
    private TableColumn<Vehicle, String> makeColumn, modelColumn, engineColumn, typeColumn;
    @FXML
    private TableColumn<Vehicle, Double> priceColumn;

    @FXML
    private TableColumn<Car, Integer> numberOfDoorsColumn;
    @FXML
    private TableColumn<Car, String> driveTypeColumn, colorColumn;

    @FXML
    private TableColumn<Motorcycle, String> motorcycleTypeColumn;
    @FXML
    private TableColumn<Motorcycle, Integer> engineCapacityColumn;
    @FXML
    private TableColumn<Motorcycle, Boolean> hasABSColumn, isA2CompatibleColumn;

    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Employee, String> firstNameColumn, lastNameColumn, hireDateColumn;
    @FXML
    private TableColumn<Employee, Double> commissionColumn;

    @FXML
    private TableColumn<Customer, String> customerFirstNameColumn, customerLastNameColumn,
            customerEmailColumn, customerPhoneColumn, customerCnpColumn;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn, employeeIdOrderColumn;
    @FXML
    private TableColumn<Order, String> customerCnpOrderColumn, statusColumn, orderDateColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

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

    private void updateTotalPrice() {
        if (vehicleIDField.getText() != null && !vehicleIDField.getText().isEmpty()) {
            int vehicleID = Integer.parseInt(vehicleIDField.getText());
            double price = DatabaseManager.getVehiclePrice(vehicleID);
            vehiclePriceField.setText(String.valueOf(price));
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

        numberOfDoorsColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Car) {
                Car c = (Car) v;
                return new ReadOnlyIntegerWrapper(c.getNumberOfDoors()).asObject();
            } else {
                return new ReadOnlyIntegerWrapper(0).asObject();
            }
        });
        numberOfDoorsColumn.setVisible(false);

        driveTypeColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Car) {
                Car c = (Car) v;
                return new ReadOnlyStringWrapper(c.getDriveType());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        driveTypeColumn.setVisible(false);

        colorColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Car) {
                Car c = (Car) v;
                return new ReadOnlyStringWrapper(c.getColor());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        colorColumn.setVisible(false);

        motorcycleTypeColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyStringWrapper(m.getBikeType());
            } else {
                return new ReadOnlyStringWrapper("");
            }
        });
        motorcycleTypeColumn.setVisible(false);

        engineCapacityColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyIntegerWrapper(m.getEngineCapacity()).asObject();
            } else {
                return new ReadOnlyIntegerWrapper(0).asObject();
            }
        });
        engineCapacityColumn.setVisible(false);

        hasABSColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyBooleanWrapper(m.hasABS()).asObject();
            } else {
                return new ReadOnlyBooleanWrapper(false).asObject();
            }
        });
        hasABSColumn.setVisible(false);

        isA2CompatibleColumn.setCellValueFactory(cd -> {
            Vehicle v = cd.getValue();
            if (v instanceof Motorcycle) {
                Motorcycle m = (Motorcycle) v;
                return new ReadOnlyBooleanWrapper(m.isA2Compatible()).asObject();
            } else {
                return new ReadOnlyBooleanWrapper(false).asObject();
            }
        });
        isA2CompatibleColumn.setVisible(false);

        hideVehicleTable();

        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        commissionColumn.setCellValueFactory(new PropertyValueFactory<>("commission"));

        hideEmployeeTable();

        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerCnpColumn.setCellValueFactory(new PropertyValueFactory<>("cnp"));

        hideCustomerTable();

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        employeeIdOrderColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        customerCnpOrderColumn.setCellValueFactory(new PropertyValueFactory<>("customerCnp"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        hideOrderTable();

        engineFilter.getItems().addAll("ANY", "PETROL", "DIESEL", "ELECTRIC", "HYBRID");
        typeFilter.getItems().addAll("ANY", "CAR", "MOTORCYCLE");

        showAllBtn.setOnAction(this::handleShowAll);
        filterBtn.setOnAction(this::handleFilter);
        createOrderBtn.setOnAction(this::handleCreateOrder);
        showEmployeesBtn.setOnAction(this::showEmployees);
        showCustomersBtn.setOnAction(this::showCustomers);
        showOrdersBtn.setOnAction(this::showOrders);

        List<String> employees = DatabaseManager.getEmployeesNames();
        employeesChoiceBox.setItems(FXCollections.observableArrayList(employees));
        employeesChoiceBox.setValue("Select Employee");

        employeesChoiceBox.setOnAction(event -> {
            String selectedEmployee = employeesChoiceBox.getValue();
            if (selectedEmployee != null && !selectedEmployee.isEmpty()) {
                int index = employees.indexOf(selectedEmployee);
                List<Double> commissions = DatabaseManager.getEmployeesCommissions();
                if (index >= 0 && index < commissions.size()) {
                    commissionField.setText(String.valueOf(commissions.get(index)) + "%");
                    updateTotalPrice();
                } else {
                    commissionField.setText("N/A");
                }
            } else {
                commissionField.setText("N/A");
            }
        });

        vehicleIDField.setOnAction(event -> {
            updateTotalPrice();
        });

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

        hideEmployeeTable();
        hideCustomerTable();
        hideOrderTable();

        try {
            String selectedType = typeFilter.getValue();
            if (selectedType.equals("ANY") || selectedType == null || selectedType.isEmpty()) {
                hideCarColumns();
                hideMotorcycleColumns();
            } else if (selectedType != null && !selectedType.isEmpty() && !selectedType.equals("ANY")) {
                allVehicles = allVehicles.stream()
                        .filter(v -> v.getType().equalsIgnoreCase(selectedType))
                        .collect(Collectors.toList());
                if (selectedType.equals("CAR")) {
                    numberOfDoorsColumn.setVisible(true);
                    driveTypeColumn.setVisible(true);
                    colorColumn.setVisible(true);
                    hideMotorcycleColumns();
                } else if (selectedType.equals("MOTORCYCLE")) {
                    hideCarColumns();
                    motorcycleTypeColumn.setVisible(true);
                    engineCapacityColumn.setVisible(true);
                    hasABSColumn.setVisible(true);
                    isA2CompatibleColumn.setVisible(true);
                }
            }
        } catch (Exception e) {
        }

        try {
            String selectedEngine = engineFilter.getValue();
            if (selectedEngine.equals("ANY") || selectedEngine == null || selectedEngine.isEmpty()) {
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

        try {
            int minYear = Integer.parseInt(minYearField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getYear() >= minYear)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int maxYear = Integer.parseInt(maxYearField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getYear() <= maxYear)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            double minPrice = Double.parseDouble(minPriceField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            double maxPrice = Double.parseDouble(maxPriceField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int minMileage = Integer.parseInt(minMileageField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMileage() >= minMileage)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int maxMileage = Integer.parseInt(maxMileageField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMileage() <= maxMileage)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int minPower = Integer.parseInt(minPowerField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPower() >= minPower)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int maxPower = Integer.parseInt(maxPowerField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getPower() <= maxPower)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int minTorque = Integer.parseInt(minTorqueField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getTorque() >= minTorque)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        try {
            int maxTorque = Integer.parseInt(maxTorqueField.getText());
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getTorque() <= maxTorque)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ignored) {
        }

        vehicleTable.setItems(FXCollections.observableArrayList(allVehicles));
        vehicleTable.setVisible(true);
        vehicleTable.setManaged(true);

    }

    @FXML
    private void handleCreateOrder(ActionEvent event) {
        if (vehicleIDField.getText().isEmpty() || customerCNPField.getText().isEmpty() ||
                employeesChoiceBox.getValue() == null || employeesChoiceBox.getValue().isEmpty() ||
                vehiclePriceField.getText().isEmpty()) {
            showError("Please fill in all required fields.");
            return;
        }
        boolean isNewCustomer = newCustomerCheckBox.isSelected();
        String cnp = customerCNPField.getText();
        int selectedEmployee = DatabaseManager.getEmployeeID(employeesChoiceBox.getValue());
        double price = Double.parseDouble(totalPriceField.getText());

        if (!isNewCustomer) {
            if (!DatabaseManager.checkCustomerExists(cnp)) {
                showError(cnp + " does not exist in the database. Please create a new customer.");
                return;
            }
            DatabaseManager.createOrder(cnp, selectedEmployee, "2023-03-17", "PENDING", price);
        } else {
            if (DatabaseManager.checkCustomerExists(cnp)) {
                showError(cnp + " already exists in the database.");
                return;
            }
            try {
                String firstName = customerFirstNameField.getText(),
                        lastName = customerLastNameField.getText(),
                        email = customerEmailField.getText(),
                        phone = customerPhoneField.getText();

                DatabaseManager.createCustomer(cnp, firstName, lastName, email, phone);
            } catch (Exception e) {
                showError("Failed to create new customer: " + e.getMessage());
                return;
            }
            try {
                DatabaseManager.createOrder(cnp, selectedEmployee, "2023-03-17", "PENDING", price);
            } catch (Exception e) {
                showError("Failed to create order: " + e.getMessage());
            }
        }
    }

    @FXML
    private void showEmployees(ActionEvent event) {
        employeeTable.setItems(FXCollections.observableArrayList(DatabaseManager.getEmployees()));
        employeeTable.setVisible(true);
        employeeTable.setManaged(true);

        hideOrderTable();
        hideVehicleTable();
        hideCustomerTable();
    }

    @FXML
    private void showAllVehicles() {
        vehicleTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllVehicles()));
        vehicleTable.setVisible(true);
        vehicleTable.setManaged(true);

        hideCarColumns();
        hideMotorcycleColumns();

        hideEmployeeTable();
        hideCustomerTable();
        hideOrderTable();
    }

    @FXML
    private void showCustomers(ActionEvent event) {
        customerTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllCustomers()));
        customerTable.setVisible(true);
        customerTable.setManaged(true);

        hideVehicleTable();
        hideEmployeeTable();
        hideOrderTable();
    }

    @FXML
    private void showOrders(ActionEvent event) {
        orderTable.setItems(FXCollections.observableArrayList(DatabaseManager.getAllOrders()));
        orderTable.setVisible(true);
        orderTable.setManaged(true);

        hideVehicleTable();
        hideEmployeeTable();
        hideCustomerTable();
    }
}
