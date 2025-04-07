package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.TransportationDto;
import com.thy.route_calculator.dto.response.TransportationResponseDto;
import com.thy.route_calculator.mapper.TransportationMapper;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.model.enums.TransportationType;
import com.thy.route_calculator.service.LocationService;
import com.thy.route_calculator.service.TransportationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportations")
@Tag(name = "Transportation", description = "Endpoints for managing transportations")
@RequiredArgsConstructor
public class TransportationController {

  private final TransportationService transportationService;
  private final LocationService locationService;

  @PostMapping
  public ResponseEntity<TransportationResponseDto> createTransportation(
      @RequestBody TransportationDto dto) {
    Location origin = locationService.findById(dto.getOriginLocationId());
    Location destination = locationService.findById(dto.getDestinationLocationId());

    Transportation entity = TransportationMapper.toEntity(dto, origin, destination);
    Transportation saved = transportationService.save(entity);
    return ResponseEntity.ok(TransportationMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransportationResponseDto> getTransportation(@PathVariable Long id) {
    Transportation transportation = transportationService.findById(id);
    return ResponseEntity.ok(TransportationMapper.toDto(transportation));
  }

  @GetMapping
  public List<TransportationResponseDto> getAllTransportations() {
    return transportationService.findAll().stream()
        .map(TransportationMapper::toDto)
        .collect(Collectors.toList());
  }

  @PutMapping("/{id}")
  public ResponseEntity<TransportationResponseDto> updateTransportation(
      @PathVariable Long id, @RequestBody TransportationDto dto) {
    Location origin = locationService.findById(dto.getOriginLocationId());
    Location destination = locationService.findById(dto.getDestinationLocationId());

    Transportation updatedEntity = TransportationMapper.toEntity(dto, origin, destination);

    Transportation updated = transportationService.update(id, updatedEntity);
    return ResponseEntity.ok(TransportationMapper.toDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransportation(@PathVariable Long id) {
    transportationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/enabled-types")
  public List<TransportationType> getEnabledTransportationTypes() {
    return transportationService.getEnabledTransportationTypes();
  }
}
