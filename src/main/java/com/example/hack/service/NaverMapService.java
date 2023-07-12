package com.example.hack.service;

import com.example.hack.entity.University;
import com.example.hack.entity.Restaurant;
import com.example.hack.repository.RestaurantRepository;
import com.example.hack.service.NaverMapApiClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaverMapService {
    private final RestaurantRepository restaurantRepository;
    private final NaverMapApiClient naverMapApiClient;

    @Autowired
    public NaverMapService(RestaurantRepository restaurantRepository, NaverMapApiClient naverMapApiClient) {
        this.restaurantRepository = restaurantRepository;
        this.naverMapApiClient = naverMapApiClient;
    }

    public void saveRestaurantsWithinRadius(University university) {
        double distance = 1.0; // 반경 1킬로미터
        List<Restaurant> nearbyRestaurants = naverMapApiClient.getRestaurantsWithinDistance(university.getLatitude(), university.getLongitude(), distance);

        // 음식점 저장
        restaurantRepository.saveAll(nearbyRestaurants);
    }
}
