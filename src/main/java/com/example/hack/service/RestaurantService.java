package com.example.hack.service;

import com.example.hack.entity.Menu;
import com.example.hack.entity.Restaurant;
import com.example.hack.entity.University;
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
    private final NaverMapApiClient naverMapApiClient;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, NaverMapApiClient naverMapApiClient) {
        this.restaurantRepository = restaurantRepository;
        this.naverMapApiClient = naverMapApiClient;
    }

    public Restaurant createRestaurant(String name, double latitude, double longitude) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setLatitude(latitude);
        restaurant.setLongitude(longitude);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getNearbyRestaurants(University university) {
        List<Restaurant> nearbyRestaurants = naverMapApiClient.getRestaurantsWithinDistance(university.getLatitude(), university.getLongitude());
        return nearbyRestaurants;
    }

    public List<Restaurant> getRestaurantsWithinBudget(University university, double distance, double budget) {
        List<Restaurant> nearbyRestaurants = getNearbyRestaurants(university);
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
