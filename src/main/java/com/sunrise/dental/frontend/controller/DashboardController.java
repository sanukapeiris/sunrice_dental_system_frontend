package com.sunrise.dental.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class DashboardController {

    @FXML
    private void openPatients(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/patients.fxml")
            );

            Parent patients = loader.load();

            Scene scene = new Scene(patients);

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
                    "Sunrise Dental Management System - Patients"
            );

            stage.setScene(scene);
            stage.setWidth(900);
            stage.setHeight(650);
            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Error",
                    "Unable to open Patient Management."
            );
        }
    }


    @FXML
    private void openDentists() {

        showMessage(
                "Dentists",
                "Dentist Management will be opened here."
        );
    }


    @FXML
    private void openTreatments() {

        showMessage(
                "Treatments",
                "Treatment Management will be opened here."
        );
    }


    @FXML
    private void openAppointments() {

        showMessage(
                "Appointments",
                "Appointment Management will be opened here."
        );
    }


    @FXML
    private void openBilling() {

        showMessage(
                "Billing",
                "Billing Management will be opened here."
        );
    }


    @FXML
    private void openReports() {

        showMessage(
                "Reports",
                "Reports will be opened here."
        );
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

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        alert.setTitle("Exit System");

        alert.setHeaderText(
                "Exit Sunrise Dental Management System"
        );

        alert.setContentText(
                "Are you sure you want to exit?"
        );

        alert.showAndWait().ifPresent(response -> {

            if (response == javafx.scene.control.ButtonType.OK) {

                Stage stage = (Stage)
                        alert.getDialogPane()
                                .getScene()
                                .getWindow();

                stage.close();
            }
        });
    }


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