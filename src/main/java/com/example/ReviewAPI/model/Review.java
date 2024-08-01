package com.example.ReviewAPI.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private ReviewStatus status;

    private String submittedBy;
    private Long restaurantId;
    private Double eggScore;
    private Double peanutScore;
    private Double dairyScore;
    private String commentary;

    public Review(Long id, String submittedBy, Long RestaurantId, Double eggScore,
                  Double peanutScore, Double dairyScore, ReviewStatus status, String commentary) {
        this.id = id;
        this.commentary = commentary;
        this.submittedBy = submittedBy;
        this.restaurantId = RestaurantId;
        this.eggScore = eggScore;
        this.peanutScore = peanutScore;
        this.dairyScore = dairyScore;
        this.status = status;
    }

}
