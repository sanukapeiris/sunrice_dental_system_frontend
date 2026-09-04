package com.sunrise.dental.frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.dental.frontend.model.ReportSummary;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReportService {

    private static final String BASE_URL =
            "http://localhost:8081/api/reports";

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ReportService() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public ReportSummary getSummary() throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/summary"
                                )
                        )
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
                    ReportSummary.class
            );
        }

        throw new RuntimeException(
                "Failed to load report: "
                        + response.body()
        );
    }
}