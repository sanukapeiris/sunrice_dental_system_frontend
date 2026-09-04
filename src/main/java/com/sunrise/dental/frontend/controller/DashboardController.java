package com.sunrise.dental.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class DashboardController {

    // =========================
    // PATIENTS
    // =========================

    @FXML
    private void openPatients(ActionEvent event) {
        openPage(event, "/fxml/patients.fxml",
                "Sunrise Dental Management System - Patients",
                1000, 700);
    }


    // =========================
    // DENTISTS
    // =========================

    @FXML
    private void openDentists(ActionEvent event) {
        openPage(event, "/fxml/dentists.fxml",
                "Sunrise Dental Management System - Dentists",
                1000, 700);
    }


    // =========================
    // TREATMENTS
    // =========================

    @FXML
    private void openTreatments(ActionEvent event) {
        openPage(event, "/fxml/treatments.fxml",
                "Sunrise Dental Management System - Treatments",
                1000, 700);
    }


    // =========================
    // APPOINTMENTS
    // =========================

    @FXML
    private void openAppointments(ActionEvent event) {
        openPage(event, "/fxml/appointments.fxml",
                "Sunrise Dental Management System - Appointments",
                1000, 700);
    }


    // =========================
    // BILLING
    // =========================

    @FXML
    private void openBilling(ActionEvent event) {
        openPage(event, "/fxml/billing.fxml",
                "Sunrise Dental Management System - Billing",
                1000, 700);
    }


    // =========================
    // REPORTS
    // =========================

    @FXML
    private void openReports(ActionEvent event) {
        openPage(event, "/fxml/reports.fxml",
                "Sunrise Dental Management System - Reports",
                1000, 700);
    }


    // =========================
    // HELP
    // =========================

    @FXML
    private void openHelp(ActionEvent event) {
        openPage(event, "/fxml/help.fxml",
                "Sunrise Dental Management System - Help",
                1000, 700);
    }


    // =========================
    // EXIT
    // =========================

    @FXML
    private void handleExit() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Exit System");

        alert.setHeaderText(
                "Exit Sunrise Dental Management System"
        );

        alert.setContentText(
                "Are you sure you want to exit?"
        );

        alert.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                Stage stage = (Stage)
                        alert.getDialogPane()
                                .getScene()
                                .getWindow();

                stage.close();
            }
        });
    }


    // =========================
    // OPEN PAGE METHOD
    // =========================

    private void openPage(
            ActionEvent event,
            String fxmlFile,
            String title,
            double width,
            double height
    ) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlFile)
            );

            Parent page = loader.load();

            Scene scene = new Scene(page);

            if (getClass().getResource("/css/style.css") != null) {

                scene.getStylesheets().add(
                        getClass()
                                .getResource("/css/style.css")
                                .toExternalForm()
                );
            }

            Stage stage = (Stage)
                    ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setTitle(title);

            stage.setScene(scene);

            stage.setWidth(width);
            stage.setHeight(height);

            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Error",
                    "Unable to open:\n" + fxmlFile +
                            "\n\nCheck that the FXML file exists."
            );
        }
    }


    // =========================
    // MESSAGE
    // =========================

    private void showMessage(
            String title,
            String message
    ) {

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }
}