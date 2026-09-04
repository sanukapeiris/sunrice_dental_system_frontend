package com.sunrise.dental.frontend.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.sunrise.dental.frontend.model.Dentist;
import com.sunrise.dental.frontend.service.DentistService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;



public class DentistController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField specializationField;

    @FXML
    private TextField contactField;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private TableView<Dentist> dentistTable;

    @FXML
    private TableColumn<Dentist, Long> idColumn;

    @FXML
    private TableColumn<Dentist, String> nameColumn;

    @FXML
    private TableColumn<Dentist, String> specializationColumn;

    @FXML
    private TableColumn<Dentist, String> contactColumn;

    @FXML
    private TableColumn<Dentist, Boolean> activeColumn;

    @FXML
    private Label messageLabel;

    private final DentistService dentistService =
            new DentistService();

    private Dentist selectedDentist;

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        specializationColumn.setCellValueFactory(
                new PropertyValueFactory<>("specialization"));

        contactColumn.setCellValueFactory(
                new PropertyValueFactory<>("contactNumber"));

        activeColumn.setCellValueFactory(
                new PropertyValueFactory<>("active"));

        dentistTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {

                    if (newValue != null) {
                        selectedDentist = newValue;

                        nameField.setText(
                                newValue.getName());

                        specializationField.setText(
                                newValue.getSpecialization());

                        contactField.setText(
                                newValue.getContactNumber());

                        activeCheckBox.setSelected(
                                newValue.isActive());
                    }
                });

        loadDentists();
    }

    @FXML
    public void loadDentists() {

        try {

            dentistTable.setItems(
                    FXCollections.observableArrayList(
                            dentistService.getAllDentists()
                    )
            );

            messageLabel.setText(
                    "Dentists loaded successfully."
            );

        } catch (Exception e) {

            messageLabel.setText(
                    "Error loading dentists: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void addDentist() {

        if (!validateFields()) {
            return;
        }

        try {

            Dentist dentist = new Dentist(
                    nameField.getText().trim(),
                    specializationField.getText().trim(),
                    contactField.getText().trim(),
                    activeCheckBox.isSelected()
            );

            dentistService.addDentist(dentist);

            messageLabel.setText(
                    "Dentist added successfully."
            );

            clearFields();
            loadDentists();

        } catch (Exception e) {

            messageLabel.setText(
                    "Error adding dentist: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void updateDentist() {

        if (selectedDentist == null) {

            showAlert(
                    "Select Dentist",
                    "Please select a dentist from the table first."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {

            Dentist dentist = new Dentist(
                    selectedDentist.getId(),
                    nameField.getText().trim(),
                    specializationField.getText().trim(),
                    contactField.getText().trim(),
                    activeCheckBox.isSelected()
            );

            dentistService.updateDentist(
                    selectedDentist.getId(),
                    dentist
            );

            messageLabel.setText(
                    "Dentist updated successfully."
            );

            clearFields();
            loadDentists();

        } catch (Exception e) {

            messageLabel.setText(
                    "Error updating dentist: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void deleteDentist() {

        if (selectedDentist == null) {

            showAlert(
                    "Select Dentist",
                    "Please select a dentist from the table first."
            );

            return;
        }

        Alert confirmation =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmation.setTitle("Delete Dentist");
        confirmation.setHeaderText(
                "Delete " + selectedDentist.getName() + "?"
        );
        confirmation.setContentText(
                "Are you sure you want to delete this dentist?"
        );

        confirmation.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                try {

                    dentistService.deleteDentist(
                            selectedDentist.getId()
                    );

                    messageLabel.setText(
                            "Dentist deleted successfully."
                    );

                    clearFields();
                    loadDentists();

                } catch (Exception e) {

                    messageLabel.setText(
                            "Error deleting dentist: "
                                    + e.getMessage()
                    );
                }
            }
        });
    }

    @FXML
    private void clearFields() {

        nameField.clear();
        specializationField.clear();
        contactField.clear();

        activeCheckBox.setSelected(true);

        selectedDentist = null;

        dentistTable.getSelectionModel()
                .clearSelection();

        messageLabel.setText("");
    }

    private boolean validateFields() {

        String name = nameField.getText().trim();
        String specialization =
                specializationField.getText().trim();
        String contact =
                contactField.getText().trim();

        if (name.isEmpty()) {

            showAlert(
                    "Validation Error",
                    "Dentist name is required."
            );

            return false;
        }

        if (specialization.isEmpty()) {

            showAlert(
                    "Validation Error",
                    "Specialization is required."
            );

            return false;
        }

        if (contact.isEmpty()) {

            showAlert(
                    "Validation Error",
                    "Contact number is required."
            );

            return false;
        }

        if (!contact.matches("\\d{10}")) {

            showAlert(
                    "Validation Error",
                    "Contact number must contain exactly 10 digits."
            );

            return false;
        }

        return true;
    }

    private void showAlert(
            String title,
            String message) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING);

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/dashboard.fxml")
            );

            Parent dashboard = loader.load();

            Scene scene = new Scene(dashboard);

            scene.getStylesheets().add(
                    getClass()
                            .getResource("/css/style.css")
                            .toExternalForm()
            );

            Stage stage = (Stage)
                    ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setTitle(
                    "Sunrise Dental Management System"
            );

            stage.setScene(scene);

            stage.setWidth(1000);
            stage.setHeight(700);

            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();

            showAlert(
                    "Error",
                    "Unable to return to Dashboard."
            );
        }
    }
}