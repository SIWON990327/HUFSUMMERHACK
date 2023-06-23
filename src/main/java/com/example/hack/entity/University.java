package com.example.hack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long univid;

    @Column(nullable = false)
    private String univname;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Restaurant> restaurant;

    public String getUnivname() {
        return univname;
    }

    public void setUnivname(String univname){
        this.univname = univname;
    }



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
