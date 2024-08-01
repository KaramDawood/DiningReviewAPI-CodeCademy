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
public class UserD {

    @Id
    @GeneratedValue
    private Long id;

    private String displayName;
    private String city;
    private String state;
    private String zipCode;

    private Boolean interestPeanut;
    private Boolean interestEgg;
    private Boolean interestDairy;

    public UserD(Long id, String displayName, String city, String state,
                 String zipCode, Boolean interestPeanut, Boolean interestEgg, Boolean interestDairy) {
        this.id = null;
        this.displayName = displayName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.interestPeanut = interestPeanut;
        this.interestEgg = interestEgg;
        this.interestDairy = interestDairy;

    }
}
