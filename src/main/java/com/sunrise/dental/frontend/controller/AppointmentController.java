package com.sunrise.dental.frontend.controller;

import com.sunrise.dental.frontend.model.Appointment;
import com.sunrise.dental.frontend.model.AppointmentRequest;
import com.sunrise.dental.frontend.service.AppointmentService;

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

public class AppointmentController {

    @FXML
    private TextField patientIdField;

    @FXML
    private TextField dentistIdField;

    @FXML
    private TextField treatmentIdField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> numberColumn;

    @FXML
    private TableColumn<Appointment, Long> patientColumn;

    @FXML
    private TableColumn<Appointment, Long> dentistColumn;

    @FXML
    private TableColumn<Appointment, Long> treatmentColumn;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

    @FXML
    private TableColumn<Appointment, String> timeColumn;

    @FXML
    private TableColumn<Appointment, String> statusColumn;

    private final AppointmentService service =
            new AppointmentService();

    @FXML
    public void initialize() {

        numberColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentNumber")
        );

        patientColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId")
        );

        dentistColumn.setCellValueFactory(
                new PropertyValueFactory<>("dentistId")
        );

        treatmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("treatmentId")
        );

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate")
        );

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentTime")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        loadAppointments();
    }

    @FXML
    public void loadAppointments() {

        try {

            appointmentTable.setItems(
                    FXCollections.observableArrayList(
                            service.getAllAppointments()
                    )
            );

            messageLabel.setText(
                    "Appointments loaded successfully."
            );

        } catch (Exception e) {

            messageLabel.setText(
                    "Unable to load appointments."
            );

            e.printStackTrace();
        }
    }

    @FXML
    private void createAppointment() {

        if (!validateFields()) {
            return;
        }

        try {

            AppointmentRequest request =
                    new AppointmentRequest(
                            Long.parseLong(
                                    patientIdField
                                            .getText()
                                            .trim()
                            ),
                            Long.parseLong(
                                    dentistIdField
                                            .getText()
                                            .trim()
                            ),
                            Long.parseLong(
                                    treatmentIdField
                                            .getText()
                                            .trim()
                            ),
                            dateField.getText().trim(),
                            timeField.getText().trim()
                    );

            Appointment appointment =
                    service.createAppointment(request);

            messageLabel.setText(
                    "Appointment created: "
                            + appointment
                            .getAppointmentNumber()
            );

            clearFields();
            loadAppointments();

        } catch (Exception e) {

            showError(
                    "Unable to create appointment",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void searchAppointment() {

        String number =
                messageInput();

        if (number.isEmpty()) {

            showError(
                    "Appointment Number",
                    "Please enter an appointment number."
            );

            return;
        }

        try {

            Appointment appointment =
                    service.getAppointment(number);

            patientIdField.setText(
                    String.valueOf(
                            appointment.getPatientId()
                    )
            );

            dentistIdField.setText(
                    String.valueOf(
                            appointment.getDentistId()
                    )
            );

            treatmentIdField.setText(
                    String.valueOf(
                            appointment.getTreatmentId()
                    )
            );

            dateField.setText(
                    appointment.getAppointmentDate()
            );

            timeField.setText(
                    appointment.getAppointmentTime()
            );

            messageLabel.setText(
                    "Appointment found: " + number
            );

        } catch (Exception e) {

            showError(
                    "Appointment Not Found",
                    e.getMessage()
            );
        }
    }

    private String messageInput() {

        TextField dialogField =
                new TextField();

        dialogField.setPromptText(
                "Example: APT-2026-00001"
        );

        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Search Appointment");
        alert.setHeaderText(
                "Enter Appointment Number"
        );

        alert.getDialogPane()
                .setContent(dialogField);

        alert.showAndWait();

        return dialogField.getText().trim();
    }

    @FXML
    private void cancelAppointment() {

        Appointment selected =
                appointmentTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showError(
                    "Select Appointment",
                    "Please select an appointment first."
            );

            return;
        }

        try {

            service.cancelAppointment(
                    selected.getAppointmentNumber()
            );

            messageLabel.setText(
                    "Appointment cancelled."
            );

            loadAppointments();

        } catch (Exception e) {

            showError(
                    "Unable to cancel appointment",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void clearFields() {

        patientIdField.clear();
        dentistIdField.clear();
        treatmentIdField.clear();
        dateField.clear();
        timeField.clear();

        appointmentTable.getSelectionModel()
                .clearSelection();

        messageLabel.setText("");
    }

    private boolean validateFields() {

        if (patientIdField.getText().trim().isEmpty() ||
                dentistIdField.getText().trim().isEmpty() ||
                treatmentIdField.getText().trim().isEmpty() ||
                dateField.getText().trim().isEmpty() ||
                timeField.getText().trim().isEmpty()) {

            messageLabel.setText(
                    "Please fill in all appointment fields."
            );

            return false;
        }

        try {

            Long.parseLong(
                    patientIdField.getText().trim()
            );

            Long.parseLong(
                    dentistIdField.getText().trim()
            );

            Long.parseLong(
                    treatmentIdField.getText().trim()
            );

        } catch (NumberFormatException e) {

            messageLabel.setText(
                    "Patient, Dentist and Treatment IDs must be numbers."
            );

            return false;
        }

        return true;
    }

    private void showError(
            String title,
            String message) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) {

        try {

            Parent dashboard =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/fxml/dashboard.fxml"
                            )
                    );

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

            showError(
                    "Error",
                    "Unable to return to Dashboard."
            );
        }
    }
}