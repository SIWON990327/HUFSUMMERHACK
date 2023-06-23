package com.example.hack.entity;

import jakarta.persistence.*;

public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long univid;

    private String univname;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = 'university', cascade = CascadeType.ALL)
    private List<Restaurant> restaurant;


}
