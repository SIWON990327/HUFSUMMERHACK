package com.example.hack.controller;

import com.example.hack.entity.Restaurant;
import com.example.hack.entity.University;
import com.example.hack.service.RestaurantService;
import com.example.hack.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UniversityService universityService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, UniversityService universityService) {
        this.restaurantService = restaurantService;
        this.universityService = universityService;
    }

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant.getName(), restaurant.getLatitude(), restaurant.getLongitude());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Restaurant>> getNearbyRestaurants(@RequestParam String universityName) {
        University university = universityService.getUniversityByName(universityName);
        List<Restaurant> nearbyRestaurants = restaurantService.getNearbyRestaurants(university);
        for (Restaurant restaurant : nearbyRestaurants) {
            restaurantService.createRestaurant(restaurant.getName(), restaurant.getLatitude(), restaurant.getLongitude());
        }
        return ResponseEntity.ok(nearbyRestaurants);
    }

    @GetMapping("/within-budget")
    public ResponseEntity<List<Restaurant>> getRestaurantsWithinBudget(@RequestParam String universityName,
                                                                       @RequestParam double distance,
                                                                       @RequestParam double budget) {
        University university = universityService.getUniversityByName(universityName);
        List<Restaurant> affordableRestaurants = restaurantService.getRestaurantsWithinBudget(university, distance, budget);
        return ResponseEntity.ok(affordableRestaurants);
    }
}
