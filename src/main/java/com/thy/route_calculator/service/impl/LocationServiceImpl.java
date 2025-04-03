package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.exception.LocationNotFoundException;
import com.thy.route_calculator.model.Location;
import com.thy.route_calculator.repository.LocationRepository;
import com.thy.route_calculator.service.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;

  @Autowired
  public LocationServiceImpl(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Override
  public Location save(Location location) {
    return locationRepository.save(location);
  }

  @Override
  public Location findById(Long id) {
    return locationRepository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));
  }

  @Override
  public List<Location> findAll() {
    return locationRepository.findAll();
  }

  @Override
  public Location update(Long id, Location updatedLocation) {
    Location existing =
        locationRepository.findById(id).orElseThrow(() -> new LocationNotFoundException(id));

    existing.setName(updatedLocation.getName());
    existing.setCountry(updatedLocation.getCountry());
    existing.setCity(updatedLocation.getCity());
    existing.setLocationCode(updatedLocation.getLocationCode());

    return locationRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    locationRepository.deleteById(id);
  }
}
