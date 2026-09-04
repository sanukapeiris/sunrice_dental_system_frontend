package com.sunrise.dental.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.dental.frontend.model.Dentist;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class DentistService {

    private static final String BASE_URL =
            "http://localhost:8081/api/dentists";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public DentistService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public List<Dentist> getAllDentists() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return mapper.readValue(
                    response.body(),
                    new TypeReference<List<Dentist>>() {}
            );
        }

        throw new RuntimeException(
                "Failed to load dentists: " + response.body()
        );
    }

    public Dentist addDentist(Dentist dentist) throws Exception {

        String json = mapper.writeValueAsString(dentist);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return mapper.readValue(
                    response.body(),
                    Dentist.class
            );
        }

        throw new RuntimeException(
                "Failed to add dentist: " + response.body()
        );
    }

    public Dentist updateDentist(
            Long id,
            Dentist dentist) throws Exception {

        String json = mapper.writeValueAsString(dentist);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

            return mapper.readValue(
                    response.body(),
                    Dentist.class
            );
        }

        throw new RuntimeException(
                "Failed to update dentist: " + response.body()
        );
    }

    public void deleteDentist(Long id) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

            throw new RuntimeException(
                    "Failed to delete dentist: " + response.body()
            );
        }
    }
}