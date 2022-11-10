package com.adidas.backend.prioritysaleservice.models;

import java.time.Instant;

public class UserBean {

    private String name;
    private String email;
    private Integer points;
    private Instant registrationDate;

    public UserBean(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserBean(String email, Integer points, Instant registrationDate) {
        this.email = email;
        this.points = points;
        this.registrationDate = registrationDate;
    }

    public UserBean(String name, String email, Integer points, Instant registrationDate) {
        this.name = name;
        this.email = email;
        this.points = points;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }
}
