package com.thy.route_calculator.controller;

import com.thy.route_calculator.dto.RouteListingDto;
import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
@Tag(name = "Route Listing", description = "Endpoint for route listing")
public class RouteController {
  @PostMapping
  public ResponseEntity<List<RouteListingResponseDto>> listRoutes(
      @RequestBody @Valid RouteListingDto routeListingDto) {

    return null;
  }
}
