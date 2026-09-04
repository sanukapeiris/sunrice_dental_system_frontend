package com.sunrise.dental.frontend.controller;

import com.sunrise.dental.frontend.model.ReportSummary;
import com.sunrise.dental.frontend.service.ReportService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReportController {

    @FXML
    private Label patientsCountLabel;

    @FXML
    private Label dentistsCountLabel;

    @FXML
    private Label appointmentsCountLabel;

    @FXML
    private Label billsCountLabel;

    @FXML
    private Label messageLabel;

    private final ReportService reportService =
            new ReportService();

    @FXML
    public void initialize() {

        loadReport();
    }

    @FXML
    private void loadReport() {

        try {

            ReportSummary summary =
                    reportService.getSummary();

            patientsCountLabel.setText(
                    String.valueOf(
                            summary.getPatients()
                    )
            );

            dentistsCountLabel.setText(
                    String.valueOf(
                            summary.getDentists()
                    )
            );

            appointmentsCountLabel.setText(
                    String.valueOf(
                            summary.getAppointments()
                    )
            );

            billsCountLabel.setText(
                    String.valueOf(
                            summary.getBills()
                    )
            );

            messageLabel.setText(
                    "Report loaded successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            messageLabel.setText(
                    "Unable to load report."
            );

            showError(
                    "Report Error",
                    getErrorMessage(e)
            );
        }
    }

    @FXML
    private void refreshReport() {

        loadReport();
    }

    @FXML
    private void goBack(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/dashboard.fxml"
                            )
                    );

            Parent dashboard =
                    loader.load();

            Scene scene =
                    new Scene(dashboard);

            scene.getStylesheets().add(
                    getClass()
                            .getResource(
                                    "/css/style.css"
                            )
                            .toExternalForm()
            );

            Stage stage =
                    (Stage)
                            ((Node) event.getSource())
                                    .getScene()
                                    .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Sunrise Dental Management System"
            );

            stage.setWidth(1000);
            stage.setHeight(700);

            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Error",
                    "Unable to return to Dashboard."
            );
        }
    }

    private String getErrorMessage(Exception e) {

        if (e.getMessage() == null) {
            return "An unexpected error occurred.";
        }

        return e.getMessage();
    }

    private void showError(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(title);

        alert.setContentText(
                message == null
                        ? "An unexpected error occurred."
                        : message
        );

        alert.showAndWait();
    }
}