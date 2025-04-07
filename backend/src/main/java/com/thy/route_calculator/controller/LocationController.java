package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.LocationDto;
import com.thy.route_calculator.dto.response.ApiResponseBuilder;
import com.thy.route_calculator.dto.response.ApiSuccessResponse;
import com.thy.route_calculator.mapper.LocationMapper;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<ApiSuccessResponse<LocationDto>> createLocation(
      @Valid @RequestBody LocationDto dto) {
    Location saved = locationService.save(LocationMapper.toEntity(dto));
    return ApiResponseBuilder.success(LocationMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<LocationDto>> getLocation(@PathVariable Long id) {
    Location location = locationService.findById(id);
    return ApiResponseBuilder.success(LocationMapper.toDto(location));
  }

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<List<LocationDto>>> getAllLocations() {
    List<LocationDto> locations =
        locationService.findAll().stream().map(LocationMapper::toDto).collect(Collectors.toList());
    return ApiResponseBuilder.success(locations);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<LocationDto>> updateLocation(
      @PathVariable Long id, @Valid @RequestBody LocationDto dto) {
    Location updated = locationService.update(id, LocationMapper.toEntity(dto));
    return ApiResponseBuilder.success(LocationMapper.toDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Object>> deleteLocation(@PathVariable Long id) {
    locationService.deleteById(id);
    return ApiResponseBuilder.success(null);
  }
}
