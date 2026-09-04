package com.sunrise.dental.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportSummary {

    @JsonProperty("Patients")
    private long patients;

    @JsonProperty("Dentists")
    private long dentists;

    @JsonProperty("Appointments")
    private long appointments;

    @JsonProperty("Bills")
    private long bills;

    public ReportSummary() {
    }

    public long getPatients() {
        return patients;
    }

    public void setPatients(long patients) {
        this.patients = patients;
    }

    public long getDentists() {
        return dentists;
    }

    public void setDentists(long dentists) {
        this.dentists = dentists;
    }

    public long getAppointments() {
        return appointments;
    }

    public void setAppointments(long appointments) {
        this.appointments = appointments;
    }

    public long getBills() {
        return bills;
    }

    public void setBills(long bills) {
        this.bills = bills;
    }
}