package com.example.ReviewAPI.repository;

import com.example.ReviewAPI.model.Review;
import com.example.ReviewAPI.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    Optional<Review> findById(Long id);
    Iterable<Review> findReviewsByStatus(ReviewStatus reviewStatus);
    List<Review> findReviewsByRestaurantIdAndStatus(Long restaurantId, ReviewStatus reviewStatus);



}
