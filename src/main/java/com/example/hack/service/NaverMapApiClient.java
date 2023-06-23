package com.example.hack.service;

import com.example.hack.entity.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class NaverMapApiClient {
    private static final String NAVER_MAP_API_URL = "https://naverapis.com/maps/geocode?query=&7im6q2ce1m=xCVJDy6TmI2FKh7JF2Kb8Jd3iEegvS9P3DqmDRbP";
    private static final String NAVER_RESTAURANT_API_URL = "https://naverapis.com/maps/places/restaurant?query=&7im6q2ce1m=xCVJDy6TmI2FKh7JF2Kb8Jd3iEegvS9P3DqmDRbP";

    public List<Restaurant> getRestaurantsWithinDistance(double latitude, double longitude, double distance) {
        String apiUrl = NAVER_RESTAURANT_API_URL + latitude + "," + longitude + "&distance=" + distance;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        Gson gson = new Gson();
        Type restaurantListType = new TypeToken<List<Restaurant>>() {}.getType();
        List<Restaurant> restaurants = gson.fromJson(response, restaurantListType);

        return restaurants;
    }
}
