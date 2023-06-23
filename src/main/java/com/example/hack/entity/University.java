package com.example.hack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = 'university', cascade = CascadeType.ALL)
    private List<Restaurant> restaurant;


}
