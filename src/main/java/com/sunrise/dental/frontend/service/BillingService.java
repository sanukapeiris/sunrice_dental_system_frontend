package com.sunrise.dental.frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.dental.frontend.model.Bill;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BillingService {

    private static final String BASE_URL =
            "http://localhost:8081/api/bills";

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public BillingService() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public Bill generateBill(String appointmentNumber)
            throws Exception {

        String url = BASE_URL + "/"
                + appointmentNumber;

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers.noBody()
                        )
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() >= 200
                && response.statusCode() < 300) {

            return objectMapper.readValue(
                    response.body(),
                    Bill.class
            );
        }

        throw new RuntimeException(
                "Failed to generate bill: "
                        + response.body()
        );
    }

    public Bill getBill(String billNumber)
            throws Exception {

        String url = BASE_URL + "/"
                + billNumber;

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() >= 200
                && response.statusCode() < 300) {

            return objectMapper.readValue(
                    response.body(),
                    Bill.class
            );
        }

        throw new RuntimeException(
                "Failed to get bill: "
                        + response.body()
        );
    }
}