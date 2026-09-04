package com.sunrise.dental.frontend.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Treatment {

    private Long id;

    @JsonProperty("treatmentName")
    @JsonAlias({"name"})
    private String name;

    @JsonProperty("treatmentCost")
    @JsonAlias({"cost"})
    private double cost;

    public Treatment() {
    }

    public Treatment(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Treatment(Long id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}