package com.example.hack.repository;

import com.example.hack.entity.Menu;
import com.example.hack.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurant(Restaurant restaurant);
}
