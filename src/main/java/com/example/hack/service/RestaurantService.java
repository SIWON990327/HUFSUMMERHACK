package com.example.hack.service;

import com.example.hack.entity.Menu;
import com.example.hack.entity.Restaurant;
import com.example.hack.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to distance
        return distance;
    }

    public List<Restaurant> getNearbyRestaurants(University university, double distance) {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Restaurant> nearbyRestaurants = new ArrayList<>();

        for (Restaurant restaurant : allRestaurants) {
            if (calculateDistance(university.getLatitude(), university.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude()) <= distance) {
                nearbyRestaurants.add(restaurant);
            }
        }

        return nearbyRestaurants;
    }

    public List<Restaurant> getRestaurantsWithinBudget(University university, double distance, double budget) {
        List<Restaurant> nearbyRestaurants = getNearbyRestaurants(university, distance);
        List<Restaurant> affordableRestaurants = new ArrayList<>();

        for (Restaurant restaurant : nearbyRestaurants) {
            for (Menu menu : restaurant.getMenu()) {
                if (menu.getPrice() <= budget) {
                    affordableRestaurants.add(restaurant);
                    break;
                }
            }
        }

        return affordableRestaurants;
    }



}
