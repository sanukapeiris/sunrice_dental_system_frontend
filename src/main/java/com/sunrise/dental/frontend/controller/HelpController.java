package com.sunrise.dental.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelpController {

    @FXML
    private TextArea helpTextArea;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {

        helpTextArea.setText(
                "SUNRISE DENTAL CLINIC - HELP GUIDE\n\n" +

                        "1. LOGIN\n" +
                        "• Enter your username and password.\n" +
                        "• Click LOGIN.\n" +
                        "• Only authorized staff members can access the system.\n\n" +

                        "2. REGISTER A PATIENT\n" +
                        "• Open the PATIENTS section from the dashboard.\n" +
                        "• Enter the patient's name, address and contact number.\n" +
                        "• Click ADD PATIENT.\n" +
                        "• Select a patient from the table to update or delete their details.\n\n" +

                        "3. MANAGE DENTISTS\n" +
                        "• Open the DENTISTS section.\n" +
                        "• Enter the dentist's name, specialization and contact number.\n" +
                        "• Click ADD DENTIST.\n" +
                        "• Dentist details can be updated or deleted when required.\n\n" +

                        "4. MANAGE TREATMENTS\n" +
                        "• Open the TREATMENTS section.\n" +
                        "• Enter the treatment name and treatment cost.\n" +
                        "• Click ADD TREATMENT.\n" +
                        "• Existing treatment details can be updated or deleted.\n\n" +

                        "5. REGISTER AN APPOINTMENT\n" +
                        "• Open the APPOINTMENTS section.\n" +
                        "• Select the patient, dentist and treatment.\n" +
                        "• Enter the appointment date and time.\n" +
                        "• Click CREATE APPOINTMENT.\n" +
                        "• The system automatically generates a unique appointment number.\n" +
                        "• A dentist cannot be booked for two appointments at the same time.\n\n" +

                        "6. VIEW APPOINTMENT DETAILS\n" +
                        "• Open the APPOINTMENTS section.\n" +
                        "• Search using the appointment number.\n" +
                        "• The patient's and appointment's information will be displayed.\n\n" +

                        "7. GENERATE A BILL\n" +
                        "• Open the BILLING section.\n" +
                        "• Enter the appointment number.\n" +
                        "• Click GENERATE BILL.\n" +
                        "• The system calculates the treatment cost and consultation fee.\n" +
                        "• The total amount is displayed on the bill.\n\n" +

                        "8. PRINT A BILL\n" +
                        "• Generate or search for a bill.\n" +
                        "• Check the displayed bill information.\n" +
                        "• Click PRINT BILL to print the receipt.\n\n" +

                        "9. VIEW REPORTS\n" +
                        "• Open the REPORTS section from the dashboard.\n" +
                        "• The system displays the total number of patients, dentists, appointments and bills.\n" +
                        "• Click REFRESH to update the report information.\n\n" +

                        "10. EXIT THE SYSTEM\n" +
                        "• Return to the dashboard.\n" +
                        "• Click EXIT.\n" +
                        "• The application will close safely.\n\n" +

                        "IMPORTANT:\n" +
                        "Always check patient and appointment details carefully before saving.\n" +
                        "Do not create duplicate appointments for the same dentist, date and time."
        );

        helpTextArea.setEditable(false);
    }

    @FXML
    private void goBack() {
        Stage stage = (Stage) helpTextArea.getScene().getWindow();
        stage.close();
    }
}