package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.TransportationDto;
import com.thy.route_calculator.dto.response.ApiResponseBuilder;
import com.thy.route_calculator.dto.response.ApiSuccessResponse;
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
  public ResponseEntity<ApiSuccessResponse<TransportationResponseDto>> createTransportation(
      @RequestBody TransportationDto dto) {
    Location origin = locationService.findById(dto.getOriginLocationId());
    Location destination = locationService.findById(dto.getDestinationLocationId());

    Transportation entity = TransportationMapper.toEntity(dto, origin, destination);
    Transportation saved = transportationService.save(entity);
    return ApiResponseBuilder.success(TransportationMapper.toDto(saved));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<TransportationResponseDto>> getTransportation(@PathVariable Long id) {
    Transportation transportation = transportationService.findById(id);
    return ApiResponseBuilder.success(TransportationMapper.toDto(transportation));
  }

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<List<TransportationResponseDto>>> getAllTransportations() {
    List<TransportationResponseDto> transportations = transportationService.findAll().stream()
        .map(TransportationMapper::toDto)
        .collect(Collectors.toList());
    return ApiResponseBuilder.success(transportations);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<TransportationResponseDto>> updateTransportation(
      @PathVariable Long id, @RequestBody TransportationDto dto) {
    Location origin = locationService.findById(dto.getOriginLocationId());
    Location destination = locationService.findById(dto.getDestinationLocationId());

    Transportation updatedEntity = TransportationMapper.toEntity(dto, origin, destination);

    Transportation updated = transportationService.update(id, updatedEntity);
    return ApiResponseBuilder.success(TransportationMapper.toDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Object>> deleteTransportation(@PathVariable Long id) {
    transportationService.deleteById(id);
    return ApiResponseBuilder.success(null);
  }

  @GetMapping("/enabled-types")
  public ResponseEntity<ApiSuccessResponse<List<TransportationType>>> getEnabledTransportationTypes() {
    return ApiResponseBuilder.success(transportationService.getEnabledTransportationTypes());
  }
}
