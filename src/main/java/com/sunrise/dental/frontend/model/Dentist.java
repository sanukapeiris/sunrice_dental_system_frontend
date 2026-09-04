package com.sunrise.dental.frontend.model;

public class Dentist {

    private Long id;
    private String name;
    private String specialization;
    private String contactNumber;
    private boolean active;

    public Dentist() {
    }

    public Dentist(String name,
                   String specialization,
                   String contactNumber,
                   boolean active) {
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.active = active;
    }

    public Dentist(Long id,
                   String name,
                   String specialization,
                   String contactNumber,
                   boolean active) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}