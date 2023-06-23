package com.example.hack.controller;

import com.example.hack.entity.Menu;
import com.example.hack.entity.Restaurant;
import com.example.hack.service.MenuService;
import com.example.hack.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;
    private final RestaurantService restaurantService;

    @Autowired
    public MenuController(MenuService menuService, RestaurantService restaurantService) {
        this.menuService = menuService;
        this.restaurantService = restaurantService;
    }


    @PostMapping("/create")
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        Restaurant restaurant = menu.getRestaurant();
        if (restaurant == null) {
            // 식당 정보가 없을 경우 에러 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Menu createdMenu = menuService.createMenu(menu.getName(), menu.getPrice(), restaurant);
        if (createdMenu == null) {
            // 메뉴 생성 실패 시 에러 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }
}
