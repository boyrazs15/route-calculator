package com.thy.route_calculator.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name = "location_code", unique = true, nullable = false)
    private String locationCode;

}
