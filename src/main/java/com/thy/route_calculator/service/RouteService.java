package com.thy.route_calculator.service;

import com.thy.route_calculator.model.RouteResult;
import java.time.LocalDateTime;
import java.util.List;

public interface RouteService {
  List<RouteResult> listRoutes(
      Long originLocationId, Long destinationLocationId, LocalDateTime date);
}
