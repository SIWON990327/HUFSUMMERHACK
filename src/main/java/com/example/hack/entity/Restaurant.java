package com.example.hack.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private double longitude;
    @Column(nullable = false, unique = true)
    private double latitude;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private University university;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Menu> menu;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurant_id(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<Menu> getMenu(){
        return this.menu;
    }
}
