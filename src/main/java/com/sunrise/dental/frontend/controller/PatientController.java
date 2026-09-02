package com.sunrise.dental.frontend.controller;

import com.sunrise.dental.frontend.model.Patient;
import com.sunrise.dental.frontend.service.PatientService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField contactField;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Long> idColumn;

    @FXML
    private TableColumn<Patient, String> nameColumn;

    @FXML
    private TableColumn<Patient, String> addressColumn;

    @FXML
    private TableColumn<Patient, String> contactColumn;

    private final PatientService patientService =
            new PatientService();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        addressColumn.setCellValueFactory(
                new PropertyValueFactory<>("address")
        );

        contactColumn.setCellValueFactory(
                new PropertyValueFactory<>("contactNumber")
        );

        patientTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, selectedPatient) -> {

                    if (selectedPatient != null) {

                        nameField.setText(
                                selectedPatient.getName()
                        );

                        addressField.setText(
                                selectedPatient.getAddress()
                        );

                        contactField.setText(
                                selectedPatient.getContactNumber()
                        );
                    }
                });

        loadPatients();
    }

    @FXML
    public void loadPatients() {

        try {

            patientTable.setItems(
                    FXCollections.observableArrayList(
                            patientService.getAllPatients()
                    )
            );

            messageLabel.setText(
                    "Patients loaded successfully."
            );

        } catch (Exception e) {

            messageLabel.setText(
                    "Unable to load patients."
            );

            e.printStackTrace();
        }
    }

    @FXML
    private void addPatient() {

        if (!validateFields()) {
            return;
        }

        try {

            Patient patient = new Patient(
                    nameField.getText().trim(),
                    addressField.getText().trim(),
                    contactField.getText().trim()
            );

            patientService.createPatient(patient);

            messageLabel.setText(
                    "Patient added successfully."
            );

            clearFields();
            loadPatients();

        } catch (Exception e) {

            showError(
                    "Unable to add patient",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void updatePatient() {

        Patient selectedPatient =
                patientTable.getSelectionModel()
                        .getSelectedItem();

        if (selectedPatient == null) {

            messageLabel.setText(
                    "Please select a patient first."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {

            selectedPatient.setName(
                    nameField.getText().trim()
            );

            selectedPatient.setAddress(
                    addressField.getText().trim()
            );

            selectedPatient.setContactNumber(
                    contactField.getText().trim()
            );

            patientService.updatePatient(
                    selectedPatient
            );

            messageLabel.setText(
                    "Patient updated successfully."
            );

            clearFields();
            loadPatients();

        } catch (Exception e) {

            showError(
                    "Unable to update patient",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void deletePatient() {

        Patient selectedPatient =
                patientTable.getSelectionModel()
                        .getSelectedItem();

        if (selectedPatient == null) {

            messageLabel.setText(
                    "Please select a patient first."
            );

            return;
        }

        try {

            patientService.deletePatient(
                    selectedPatient.getId()
            );

            messageLabel.setText(
                    "Patient deleted successfully."
            );

            clearFields();
            loadPatients();

        } catch (Exception e) {

            showError(
                    "Unable to delete patient",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void clearFields() {

        nameField.clear();
        addressField.clear();
        contactField.clear();

        patientTable.getSelectionModel()
                .clearSelection();
    }

    private boolean validateFields() {

        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();

        if (name.isEmpty()) {

            messageLabel.setText(
                    "Please enter patient name."
            );

            return false;
        }

        if (address.isEmpty()) {

            messageLabel.setText(
                    "Please enter patient address."
            );

            return false;
        }

        if (contact.isEmpty()) {

            messageLabel.setText(
                    "Please enter contact number."
            );

            return false;
        }

        if (!contact.matches("\\d{10}")) {

            messageLabel.setText(
                    "Contact number must contain 10 digits."
            );

            return false;
        }

        return true;
    }

    private void showError(String title, String message) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }
}