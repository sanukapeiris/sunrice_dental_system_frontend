package com.sunrise.dental.frontend.controller;

import com.sunrise.dental.frontend.model.Bill;
import com.sunrise.dental.frontend.service.BillingService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BillingController {

    @FXML
    private TextField appointmentNumberField;

    @FXML
    private TextField billNumberField;

    @FXML
    private Label treatmentCostLabel;

    @FXML
    private Label consultationFeeLabel;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox billReceipt;

    @FXML
    private Label receiptBillNumber;

    @FXML
    private Label receiptAppointmentNumber;

    @FXML
    private Label receiptTreatmentCost;

    @FXML
    private Label receiptConsultationFee;

    @FXML
    private Label receiptTotalAmount;

    private final BillingService billingService =
            new BillingService();

    private Bill currentBill;

    @FXML
    public void initialize() {

        clearBillDisplay();
    }

    @FXML
    private void generateBill() {

        String appointmentNumber =
                appointmentNumberField.getText().trim();

        if (appointmentNumber.isEmpty()) {

            showError(
                    "Validation Error",
                    "Please enter an appointment number."
            );

            return;
        }

        try {

            Bill bill =
                    billingService.generateBill(
                            appointmentNumber
                    );

            currentBill = bill;

            displayBill(bill);

            messageLabel.setText(
                    "Bill generated successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Unable to Generate Bill",
                    getErrorMessage(e)
            );
        }
    }

    @FXML
    private void searchBill() {

        String billNumber =
                billNumberField.getText().trim();

        if (billNumber.isEmpty()) {

            showError(
                    "Validation Error",
                    "Please enter a bill number."
            );

            return;
        }

        try {

            Bill bill =
                    billingService.getBill(
                            billNumber
                    );

            currentBill = bill;

            displayBill(bill);

            messageLabel.setText(
                    "Bill found successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Unable to Find Bill",
                    getErrorMessage(e)
            );
        }
    }

    private void displayBill(Bill bill) {

        billNumberField.setText(
                safe(bill.getBillNumber())
        );

        appointmentNumberField.setText(
                safe(bill.getAppointmentNumber())
        );

        treatmentCostLabel.setText(
                String.format(
                        "Rs. %.2f",
                        bill.getTreatmentCost()
                )
        );

        consultationFeeLabel.setText(
                String.format(
                        "Rs. %.2f",
                        bill.getConsultationFee()
                )
        );

        totalAmountLabel.setText(
                String.format(
                        "Rs. %.2f",
                        bill.getTotalAmount()
                )
        );

        receiptBillNumber.setText(
                "Bill Number: "
                        + safe(bill.getBillNumber())
        );

        receiptAppointmentNumber.setText(
                "Appointment Number: "
                        + safe(bill.getAppointmentNumber())
        );

        receiptTreatmentCost.setText(
                "Treatment Cost: Rs. "
                        + String.format(
                        "%.2f",
                        bill.getTreatmentCost()
                )
        );

        receiptConsultationFee.setText(
                "Consultation Fee: Rs. "
                        + String.format(
                        "%.2f",
                        bill.getConsultationFee()
                )
        );

        receiptTotalAmount.setText(
                "TOTAL: Rs. "
                        + String.format(
                        "%.2f",
                        bill.getTotalAmount()
                )
        );
    }

    @FXML
    private void printBill() {

        if (currentBill == null) {

            showError(
                    "No Bill",
                    "Please generate or search for a bill first."
            );

            return;
        }

        PrinterJob printerJob =
                PrinterJob.createPrinterJob();

        if (printerJob == null) {

            showError(
                    "Printing Error",
                    "No printer is available."
            );

            return;
        }

        boolean proceed =
                printerJob.showPrintDialog(
                        billReceipt.getScene().getWindow()
                );

        if (!proceed) {
            return;
        }

        try {

            PageLayout pageLayout =
                    printerJob.getPrinter()
                            .getDefaultPageLayout();

            if (pageLayout == null) {

                printerJob.endJob();

                showError(
                        "Printing Error",
                        "Unable to access printer page settings."
                );

                return;
            }

            boolean printed =
                    printerJob.printPage(
                            billReceipt
                    );

            if (printed) {

                printerJob.endJob();

                messageLabel.setText(
                        "Bill printed successfully."
                );

            } else {

                printerJob.cancelJob();

                showError(
                        "Printing Error",
                        "Unable to print the bill."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            printerJob.cancelJob();

            showError(
                    "Printing Error",
                    "An error occurred while printing."
            );
        }
    }

    @FXML
    private void clearFields() {

        appointmentNumberField.clear();
        billNumberField.clear();

        clearBillDisplay();

        messageLabel.setText("");
    }

    private void clearBillDisplay() {

        treatmentCostLabel.setText(
                "Rs. 0.00"
        );

        consultationFeeLabel.setText(
                "Rs. 0.00"
        );

        totalAmountLabel.setText(
                "Rs. 0.00"
        );

        receiptBillNumber.setText(
                "Bill Number: -"
        );

        receiptAppointmentNumber.setText(
                "Appointment Number: -"
        );

        receiptTreatmentCost.setText(
                "Treatment Cost: Rs. 0.00"
        );

        receiptConsultationFee.setText(
                "Consultation Fee: Rs. 0.00"
        );

        receiptTotalAmount.setText(
                "TOTAL: Rs. 0.00"
        );

        currentBill = null;
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

    private String safe(String value) {

        if (value == null) {
            return "-";
        }

        return value;
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