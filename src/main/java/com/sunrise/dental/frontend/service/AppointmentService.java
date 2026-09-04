package com.sunrise.dental.frontend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrise.dental.frontend.model.Appointment;
import com.sunrise.dental.frontend.model.AppointmentRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AppointmentService {

    private static final String BASE_URL =
            "http://localhost:8081/api/appointments";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public AppointmentService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public List<Appointment> getAllAppointments()
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
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
                    new TypeReference<List<Appointment>>() {}
            );
        }

        throw new RuntimeException(
                response.body()
        );
    }

    public Appointment createAppointment(
            AppointmentRequest requestData)
            throws Exception {

        String json =
                mapper.writeValueAsString(requestData);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .header(
                                "Content-Type",
                                "application/json"
                        )
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
                    Appointment.class
            );
        }

        throw new RuntimeException(
                response.body()
        );
    }

    public Appointment getAppointment(
            String appointmentNumber)
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/"
                                                + appointmentNumber
                                )
                        )
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
                    Appointment.class
            );
        }

        throw new RuntimeException(
                response.body()
        );
    }

    public void cancelAppointment(
            String appointmentNumber)
            throws Exception {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/"
                                                + appointmentNumber
                                                + "/cancel"
                                )
                        )
                        .PUT(
                                HttpRequest.BodyPublishers
                                        .noBody()
                        )
                        .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

            throw new RuntimeException(
                    response.body()
            );
        }
    }
}