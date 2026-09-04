package com.sunrise.dental.frontend.controller;

import com.sunrise.dental.frontend.model.Treatment;
import com.sunrise.dental.frontend.service.TreatmentService;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TreatmentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField costField;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Treatment> treatmentTable;

    @FXML
    private TableColumn<Treatment, Long> idColumn;

    @FXML
    private TableColumn<Treatment, String> nameColumn;

    @FXML
    private TableColumn<Treatment, Double> costColumn;

    private final TreatmentService treatmentService =
            new TreatmentService();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        costColumn.setCellValueFactory(
                new PropertyValueFactory<>("cost")
        );

        treatmentTable.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, selectedTreatment) -> {

                            if (selectedTreatment != null) {

                                nameField.setText(
                                        selectedTreatment.getName()
                                );

                                costField.setText(
                                        String.valueOf(
                                                selectedTreatment.getCost()
                                        )
                                );
                            }
                        }
                );

        loadTreatments();
    }

    @FXML
    public void loadTreatments() {

        try {

            treatmentTable.setItems(
                    FXCollections.observableArrayList(
                            treatmentService.getAllTreatments()
                    )
            );

            messageLabel.setText(
                    "Treatments loaded successfully."
            );

        } catch (Exception e) {

            messageLabel.setText(
                    "Unable to load treatments."
            );

            e.printStackTrace();
        }
    }

    @FXML
    private void addTreatment() {

        if (!validateFields()) {
            return;
        }

        try {

            double cost = Double.parseDouble(
                    costField.getText().trim()
            );

            Treatment treatment = new Treatment(
                    nameField.getText().trim(),
                    cost
            );

            treatmentService.addTreatment(treatment);

            messageLabel.setText(
                    "Treatment added successfully."
            );

            clearFields();
            loadTreatments();

        } catch (Exception e) {

            showError(
                    "Unable to add treatment",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void updateTreatment() {

        Treatment selected =
                treatmentTable.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showError(
                    "Select Treatment",
                    "Please select a treatment first."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        try {

            double cost = Double.parseDouble(
                    costField.getText().trim()
            );

            Treatment treatment = new Treatment(
                    selected.getId(),
                    nameField.getText().trim(),
                    cost
            );

            treatmentService.updateTreatment(
                    selected.getId(),
                    treatment
            );

            messageLabel.setText(
                    "Treatment updated successfully."
            );

            clearFields();
            loadTreatments();

        } catch (Exception e) {

            showError(
                    "Unable to update treatment",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void deleteTreatment() {

        Treatment selected =
                treatmentTable.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showError(
                    "Select Treatment",
                    "Please select a treatment first."
            );

            return;
        }

        Alert confirmation =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmation.setTitle("Delete Treatment");
        confirmation.setHeaderText(
                "Delete " + selected.getName() + "?"
        );
        confirmation.setContentText(
                "Are you sure you want to delete this treatment?"
        );

        confirmation.showAndWait().ifPresent(response -> {

            if (response == javafx.scene.control.ButtonType.OK) {

                try {

                    treatmentService.deleteTreatment(
                            selected.getId()
                    );

                    messageLabel.setText(
                            "Treatment deleted successfully."
                    );

                    clearFields();
                    loadTreatments();

                } catch (Exception e) {

                    showError(
                            "Unable to delete treatment",
                            e.getMessage()
                    );
                }
            }
        });
    }

    @FXML
    private void clearFields() {

        nameField.clear();
        costField.clear();

        treatmentTable.getSelectionModel()
                .clearSelection();

        messageLabel.setText("");
    }

    private boolean validateFields() {

        String name = nameField.getText().trim();
        String costText = costField.getText().trim();

        if (name.isEmpty()) {

            messageLabel.setText(
                    "Please enter treatment name."
            );

            return false;
        }

        if (costText.isEmpty()) {

            messageLabel.setText(
                    "Please enter treatment cost."
            );

            return false;
        }

        try {

            double cost = Double.parseDouble(costText);

            if (cost <= 0) {

                messageLabel.setText(
                        "Cost must be greater than zero."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            messageLabel.setText(
                    "Please enter a valid cost."
            );

            return false;
        }

        return true;
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
                message != null
                        ? message
                        : "An unexpected error occurred."
        );

        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/dashboard.fxml"
                    )
            );

            Parent dashboard = loader.load();

            Scene scene = new Scene(dashboard);

            scene.getStylesheets().add(
                    getClass()
                            .getResource(
                                    "/css/style.css"
                            )
                            .toExternalForm()
            );

            Stage stage = (Stage)
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
}