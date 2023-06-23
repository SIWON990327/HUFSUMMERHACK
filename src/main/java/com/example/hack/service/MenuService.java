package com.example.hack.service;

import com.example.hack.entity.Menu;
import com.example.hack.entity.Restaurant;
import com.example.hack.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final NaverMapApiClient naverMapApiClient;

    @Autowired
    public MenuService(MenuRepository menuRepository, NaverMapApiClient naverMapApiClient) {
        this.menuRepository = menuRepository;
        this.naverMapApiClient = naverMapApiClient;
    }

    public Menu createMenu(String name, int price, Restaurant restaurant) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(price);
        menu.setRestaurant(restaurant);

        return menuRepository.save(menu);
    }

    public List<Menu> getMenuListByRestaurant(Restaurant restaurant) {
        List<Menu> menuList = menuRepository.findByRestaurant(restaurant);
        return menuList;
    }
}
