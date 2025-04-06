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

  @GetMapping
  public ResponseEntity<List<RouteListingResponseDto>> listRoutes(
      @ModelAttribute @Valid RouteListingDto dto) {
    List<RouteResult> routeResults =
        routeService.listRoutes(
            dto.getOriginLocationId(),
            dto.getDestinationLocationId(),
            dto.getDate().atStartOfDay());
    return ResponseEntity.ok(
        routeResults.stream().map(RouteListingMapper::toDto).collect(Collectors.toList()));
  }
}
