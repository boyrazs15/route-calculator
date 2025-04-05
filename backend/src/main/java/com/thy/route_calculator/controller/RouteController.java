package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.RouteListingDto;
import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import com.thy.route_calculator.mapper.RouteListingMapper;
import com.thy.route_calculator.model.RouteResult;
import com.thy.route_calculator.service.RouteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
@Tag(name = "Route Listing", description = "Endpoint for route listing")
@RequiredArgsConstructor
public class RouteController {
  private final RouteService routeService;

  @PostMapping
  public ResponseEntity<List<RouteListingResponseDto>> listRoutes(
      @RequestBody @Valid RouteListingDto routeListingDto) {

    List<RouteResult> routeResults =
        routeService.listRoutes(
            routeListingDto.getOriginLocationId(),
            routeListingDto.getDestinationLocationId(),
            routeListingDto.getDate());
    return ResponseEntity.ok(
        routeResults.stream().map(RouteListingMapper::toDto).collect(Collectors.toList()));
  }
}
