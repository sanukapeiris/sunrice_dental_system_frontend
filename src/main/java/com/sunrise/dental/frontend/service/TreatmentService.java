package com.sunrise.dental.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.dental.frontend.model.Treatment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TreatmentService {

    private static final String BASE_URL =
            "http://localhost:8081/api/treatments";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public TreatmentService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public List<Treatment> getAllTreatments() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return mapper.readValue(
                    response.body(),
                    new TypeReference<List<Treatment>>() {}
            );
        }

        throw new RuntimeException(
                "Failed to load treatments: "
                        + response.body()
        );
    }

    public Treatment addTreatment(
            Treatment treatment) throws Exception {

        String json =
                mapper.writeValueAsString(treatment);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(
                        HttpRequest.BodyPublishers
                                .ofString(json)
                )
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return mapper.readValue(
                    response.body(),
                    Treatment.class
            );
        }

        throw new RuntimeException(
                "Failed to add treatment: "
                        + response.body()
        );
    }

    public Treatment updateTreatment(
            Long id,
            Treatment treatment) throws Exception {

        String json =
                mapper.writeValueAsString(treatment);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(
                        HttpRequest.BodyPublishers
                                .ofString(json)
                )
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return mapper.readValue(
                    response.body(),
                    Treatment.class
            );
        }

        throw new RuntimeException(
                "Failed to update treatment: "
                        + response.body()
        );
    }

    public void deleteTreatment(Long id)
            throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

            throw new RuntimeException(
                    "Failed to delete treatment: "
                            + response.body()
            );
        }
    }
}