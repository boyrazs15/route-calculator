package com.thy.route_calculator.service;

import com.thy.route_calculator.model.Location;
import java.util.List;

public interface LocationService {
  Location save(Location location);

  Location findById(Long id);

  List<Location> findAll();

  Location update(Long id, Location location);

  void deleteById(Long id);
}
