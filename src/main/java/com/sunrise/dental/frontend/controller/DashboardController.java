package com.sunrise.dental.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private void openPatients() {
        showMessage("Patients", "Patient Management will be opened here.");
    }

    @FXML
    private void openDentists() {
        showMessage("Dentists", "Dentist Management will be opened here.");
    }

    @FXML
    private void openTreatments() {
        showMessage("Treatments", "Treatment Management will be opened here.");
    }

    @FXML
    private void openAppointments() {
        showMessage("Appointments", "Appointment Management will be opened here.");
    }

    @FXML
    private void openBilling() {
        showMessage("Billing", "Billing Management will be opened here.");
    }

    @FXML
    private void openReports() {
        showMessage("Reports", "Reports will be opened here.");
    }

    @FXML
    private void openHelp() {
        showMessage(
                "Help",
                "Use the menu buttons to access the different parts of the system."
        );
    }

    @FXML
    private void handleExit() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Exit System");
        alert.setHeaderText("Exit Sunrise Dental Management System");
        alert.setContentText("Are you sure you want to exit?");

        alert.showAndWait().ifPresent(response -> {

            if (response.getText().equals("OK")) {

                Stage stage = (Stage) alert.getDialogPane()
                        .getScene()
                        .getWindow();

                stage.close();
            }
        });
    }

    private void showMessage(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }
}