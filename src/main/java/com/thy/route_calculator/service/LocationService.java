package com.thy.route_calculator.service;

import com.thy.route_calculator.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    Location save(Location location);
    Optional<Location> findById(Long id);
    List<Location> findAll();
    Location update(Long id, Location location);
    void deleteById(Long id);
}
