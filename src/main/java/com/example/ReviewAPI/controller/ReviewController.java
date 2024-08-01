package com.example.ReviewAPI.controller;

import com.example.ReviewAPI.model.Restaurant;
import com.example.ReviewAPI.model.Review;
import com.example.ReviewAPI.model.ReviewStatus;
import com.example.ReviewAPI.repository.RestaurantRepository;
import com.example.ReviewAPI.repository.ReviewRepository;
import com.example.ReviewAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ReviewController(final ReviewRepository reviewRepository, final RestaurantRepository restaurantRepository, final UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Review addReview(@RequestBody Review review) {


        if(review.getSubmittedBy().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must submit your name");
        }

        if(review.getPeanutScore() == null && review.getEggScore() == null && review.getDairyScore() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must submit one allergy score");
        }

        Optional<Restaurant> optionalRestaurant = this.restaurantRepository.findById(review.getRestaurantId());
        if(!optionalRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This restaurant does not exist");
        }

        review.setStatus(ReviewStatus.PENDING); // Still needs to be accepted by ADMIN.
        this.reviewRepository.save(review);
        return review;

    }
}
