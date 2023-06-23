package com.example.hack.service;

import com.example.hack.entity.Restaurant;
import com.example.hack.entity.University;
import com.example.hack.repository.RestaurantRepository;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class NaverMapApiClient {
    private static final String NAVER_MAP_API_URL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
    private static final String NAVER_RESTAURANT_API_URL = "https://naverapis.com/maps/places/restaurant?query=";
    private final RestaurantRepository restaurantRepository;
    public NaverMapApiClient(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    private void updateUniversityLocationAndRestaurants(University university, Restaurant restaurant) {
        // 대학 위치 업데이트
        updateUniversityLocation(university);
        List<Restaurant> nearbyRestaurants = getRestaurantsWithinDistance(university.getLatitude(), university.getLongitude());
        // 음식점 정보 업데이트
        if (nearbyRestaurants != null) {
            restaurantRepository.saveAll(nearbyRestaurants);
        }
    }
    private void updateUniversityLocation(University university) {
        String universityName = university.getUnivname();
        String apiUrl = NAVER_MAP_API_URL + universityName;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        Gson gson = new Gson();
        JsonElement jsonElement = JsonParser.parseString(response);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String status = jsonObject.get("status").getAsString();
            if (status.equals("OK")) {
                JsonArray addresses = jsonObject.getAsJsonArray("addresses");
                if (addresses.size() > 0) {
                    JsonObject addressObject = addresses.get(0).getAsJsonObject();
                    double latitude = addressObject.get("latitude").getAsDouble();
                    double longitude = addressObject.get("longitude").getAsDouble();
                    university.setLatitude(latitude);
                    university.setLongitude(longitude);
                }
            }
        }
    }
    public List<Restaurant> getRestaurantsWithinDistance(double latitude, double longitude) {
        double distance = 1.0;
        String apiUrl = NAVER_RESTAURANT_API_URL + latitude + "," + longitude + "&distance=" + distance;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        Gson gson = new Gson();
        JsonElement jsonElement = JsonParser.parseString(response);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String status = jsonObject.get("status").getAsString();
            if (status.equals("OK")) {
                JsonArray restaurantsArray = jsonObject.getAsJsonArray("restaurants");
                Type restaurantListType = new TypeToken<List<Restaurant>>() {
                }.getType();
                return gson.fromJson(restaurantsArray, restaurantListType);
            }
        }
        return null;
    }
}
