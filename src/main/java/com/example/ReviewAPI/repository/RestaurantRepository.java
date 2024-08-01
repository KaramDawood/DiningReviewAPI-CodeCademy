package com.example.ReviewAPI.repository;

import com.example.ReviewAPI.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {


            List<Restaurant> findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(String zipCode);
            List<Restaurant> findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScoreDesc(String zipCode);
            List<Restaurant> findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScoreDesc(String zipcode);
            Optional<Restaurant> findByZipCodeAndName(String zipCode, String name);
}
