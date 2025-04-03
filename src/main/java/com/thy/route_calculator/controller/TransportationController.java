package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.TransportationDto;
import com.thy.route_calculator.mapper.TransportationMapper;
import com.thy.route_calculator.model.Location;
import com.thy.route_calculator.model.Transportation;
import com.thy.route_calculator.repository.LocationRepository;
import com.thy.route_calculator.service.TransportationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportations")
@Tag(name = "Transportation", description = "Endpoints for managing transportations")
public class TransportationController {

  private final TransportationService transportationService;
  private final LocationRepository locationRepository;

  public TransportationController(
      TransportationService transportationService, LocationRepository locationRepository) {
    this.transportationService = transportationService;
    this.locationRepository = locationRepository;
  }

  @PostMapping
  public ResponseEntity<TransportationDto> createTransportation(
      @RequestBody TransportationDto dto) {
    Location origin =
        locationRepository
            .findById(dto.getOriginLocationId())
            .orElseThrow(() -> new RuntimeException("Origin location not found"));

    Location destination =
        locationRepository
            .findById(dto.getDestinationLocationId())
            .orElseThrow(() -> new RuntimeException("Destination location not found"));

    Transportation entity = TransportationMapper.toEntity(dto, origin, destination);
    Transportation saved = transportationService.save(entity);
    return ResponseEntity.ok(TransportationMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransportationDto> getTransportation(@PathVariable Long id) {
    Transportation transportation = transportationService.findById(id);
    return ResponseEntity.ok(TransportationMapper.toDto(transportation));
  }

  @GetMapping
  public List<TransportationDto> getAllTransportations() {
    return transportationService.findAll().stream()
        .map(TransportationMapper::toDto)
        .collect(Collectors.toList());
  }

  @PutMapping("/{id}")
  public ResponseEntity<TransportationDto> updateTransportation(
      @PathVariable Long id, @RequestBody TransportationDto dto) {
    Location origin =
        locationRepository
            .findById(dto.getOriginLocationId())
            .orElseThrow(() -> new RuntimeException("Origin location not found"));

    Location destination =
        locationRepository
            .findById(dto.getDestinationLocationId())
            .orElseThrow(() -> new RuntimeException("Destination location not found"));

    Transportation updatedEntity = TransportationMapper.toEntity(dto, origin, destination);

    Transportation updated = transportationService.update(id, updatedEntity);
    return ResponseEntity.ok(TransportationMapper.toDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransportation(@PathVariable Long id) {
    transportationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
