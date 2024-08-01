package com.example.ReviewAPI.controller;

import com.example.ReviewAPI.model.AdminReviewAction;
import com.example.ReviewAPI.model.Restaurant;
import com.example.ReviewAPI.model.Review;
import com.example.ReviewAPI.model.ReviewStatus;
import com.example.ReviewAPI.repository.RestaurantRepository;
import com.example.ReviewAPI.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminController(final ReviewRepository reviewRepository, final RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }


    @GetMapping("/reviews")
    public Iterable<Review> getReviewsByStatus(@RequestParam ReviewStatus reviewStatus) {

        if (reviewStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review status is required");
        }

        Iterable<Review> reviews = this.reviewRepository.findReviewsByStatus(reviewStatus);

        if (!reviews.iterator().hasNext()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found with the given status");
        }

        return reviews;
    }

    @PutMapping("/reviews/{reviewId}")
    public void reviewAction(@PathVariable Long reviewId, @RequestBody AdminReviewAction adminReviewAction) {

        Optional<Review> reviewOptional = this.reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        Review review = reviewOptional.get();

        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(reviewOptional.get().getRestaurantId());
        if (restaurantOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant doesn't exist");
        }

        if (adminReviewAction.getAcceptedReview()) {
            review.setStatus(ReviewStatus.ACCEPTED);
        } else {
            review.setStatus(ReviewStatus.DECLINED);
        }


        Restaurant restaurant = restaurantOptional.get();
        updateReviewScores(restaurant);
        this.reviewRepository.save(review);
    }


    public void updateReviewScores(Restaurant restaurant) {
        List<Review> reviews = this.reviewRepository.findReviewsByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);

        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found with the given restaurant");
        }

        double peanutScore = 0.0;
        double peanutScoreSum = 0.0;
        double peanutScoreAvg = 0.0;

        double eggScore = 0.0;
        double eggScoreSum = 0.0;
        double eggScoreAvg = 0.0;

        double dairyScore = 0.0;
        double dairyScoreSum = 0.0;
        double dairyScoreAvg = 0.0;

        for (Review review : reviews) {
            if (review.getPeanutScore() != null) {
                peanutScore += review.getPeanutScore();
                peanutScoreSum++;
            }
            if (review.getDairyScore() != null) {
                dairyScore += review.getDairyScore();
                dairyScoreSum++;
            }
            if (review.getEggScore() != null) {
                eggScore += review.getEggScore();
                eggScoreSum++;
            }

            double overallScore = (dairyScore + eggScore + peanutScore) / (dairyScoreSum + eggScoreSum + peanutScoreSum);
            restaurant.setOverallScore(round(overallScore, 2));

            if(dairyScoreSum > 0){
                dairyScoreAvg = dairyScore/dairyScoreSum;
                restaurant.setPeanutScore(round(dairyScoreAvg, 2));
            }
            if(eggScore > 0){
                eggScoreAvg = eggScore/eggScoreSum;
                restaurant.setEggScore(round(eggScoreAvg, 2));
            }

            if(peanutScore > 0){
                peanutScoreAvg = peanutScore/peanutScoreSum;
                restaurant.setPeanutScore(round(peanutScoreAvg, 2));
            }

        }
            this.restaurantRepository.save(restaurant);

     }


     public double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return (double) Math.round(value * scale) / scale;
    }

}

