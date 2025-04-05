package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.exception.LocationNotFoundException;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.repository.LocationRepository;
import com.thy.route_calculator.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;

  @Override
  public Location save(Location location) {
    log.debug("Saving location: {}", location);
    return locationRepository.save(location);
  }

  @Override
  public Location findById(Long id) {
    log.debug("Finding location by id: {}", id);
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
    log.debug("Updating location with id: {}", existing.getId());

    existing.setName(updatedLocation.getName());
    existing.setCountry(updatedLocation.getCountry());
    existing.setCity(updatedLocation.getCity());
    existing.setLocationCode(updatedLocation.getLocationCode());

    return locationRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting location with id: {}", id);
    locationRepository.deleteById(id);
  }
}
