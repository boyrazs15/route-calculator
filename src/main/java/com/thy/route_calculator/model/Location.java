package com.thy.route_calculator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name="location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    @Column(name="id")
    private Long id;

    @NotBlank
    @Column(name="name", nullable = false)
    private String name;

    @NotBlank
    @Column(name="country", nullable = false)
    private String country;

    @NotBlank
    @Column(name="city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "location_code", unique = true, nullable = false)
    private String locationCode;

}
