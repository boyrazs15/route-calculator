package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.LocationDto;
import com.thy.route_calculator.mapper.LocationMapper;
import com.thy.route_calculator.model.Location;
import com.thy.route_calculator.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Location", description = "Endpoints for managing locations")
public class LocationController {

  private final LocationService locationService;

  @Autowired
  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @PostMapping
  public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto dto) {
    Location saved = locationService.save(LocationMapper.toEntity(dto));
    return ResponseEntity.ok(LocationMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
    return locationService
        .findById(id)
        .map(LocationMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<LocationDto> getAllLocations() {
    return locationService.findAll().stream()
        .map(LocationMapper::toDto)
        .collect(Collectors.toList());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateLocation(
      @PathVariable Long id, @RequestBody LocationDto dto) {
    try {
      Location updated = locationService.update(id, LocationMapper.toEntity(dto));
      return ResponseEntity.ok(LocationMapper.toDto(updated));
    } catch (OptimisticLockingFailureException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("Location has been modified by another transaction. Please refresh and try again.");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
    locationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
