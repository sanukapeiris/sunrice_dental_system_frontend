package com.sunrise.dental.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private static final String LOGIN_URL =
            "http://localhost:8081/api/auth/login";

    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password.");
            return;
        }

        try {

            String jsonBody = String.format(
                    "{\"username\":\"%s\",\"password\":\"%s\"}",
                    username,
                    password
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LOGIN_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() >= 200 &&
                    response.statusCode() < 300) {

                messageLabel.setText("Login successful!");

                System.out.println("Login successful");
                System.out.println("Response: " + response.body());

            } else {

                messageLabel.setText(
                        "Invalid username or password."
                );
            }

        } catch (IOException | InterruptedException e) {

            messageLabel.setText(
                    "Unable to connect to the server."
            );

            e.printStackTrace();
        }
    }
}