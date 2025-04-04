package com.thy.route_calculator.service;

import com.thy.route_calculator.model.entity.Transportation;

import java.time.LocalDateTime;
import java.util.List;

public interface RouteService {
    List<Transportation> listRoutes(Long originLocationId, Long destinationLocationId, LocalDateTime date);
}
