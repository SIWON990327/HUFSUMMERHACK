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

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getMenuListByRestaurant(Restaurant restaurant) {
        return menuRepository.findByRestaurant(restaurant);
    }
}
