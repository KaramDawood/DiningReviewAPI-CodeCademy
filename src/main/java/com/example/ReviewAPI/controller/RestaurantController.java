package com.example.ReviewAPI.controller;

import com.example.ReviewAPI.model.Restaurant;
import com.example.ReviewAPI.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepo;

    public RestaurantController(final RestaurantRepository restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    @GetMapping("")
    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepo.findByZipCodeAndName(restaurant.getZipCode(), restaurant.getName());

        if (existingRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant already exists");
        }

        return restaurantRepo.save(restaurant);
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> findRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepo.findById(id);

        if (!restaurantOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This ID is not available");
        }
        return restaurantOptional;
    }

    @GetMapping("/search")
    public Iterable<Restaurant> findByZipCodeAndAllergies(
            @RequestParam String zipcode,
            @RequestParam(required = false, defaultValue = "false") Boolean interestPeanut,
            @RequestParam(required = false, defaultValue = "false") Boolean interestEgg,
            @RequestParam(required = false, defaultValue = "false") Boolean interestDairy) {

        if (interestPeanut) {
            return this.restaurantRepo.findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(zipcode);
        } else if (interestEgg) {
            return this.restaurantRepo.findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScoreDesc(zipcode);
        } else if (interestDairy) {
            return this.restaurantRepo.findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScoreDesc(zipcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one allergy interest must be specified");
        }
    }

}