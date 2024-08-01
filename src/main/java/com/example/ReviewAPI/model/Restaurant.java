package com.example.ReviewAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String zipCode;
    private String city;
    private String state;
    private String email;

    private Double peanutScore;
    private Double eggScore;
    private Double dairyScore;

    private Double overallScore;

    public Restaurant(Long id, String name, String address, String phone, String zipCode, String city,
                      String state, String email, Double peanutScore, Double eggScore, Double dairyScore, Double overallScore) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.email = email;
        this.peanutScore = peanutScore;
        this.eggScore = eggScore;
        this.dairyScore = dairyScore;
        this.overallScore = overallScore;

    }

    }
