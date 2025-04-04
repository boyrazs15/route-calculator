package com.thy.route_calculator.service;

import com.thy.route_calculator.model.Transportation;

import java.util.Date;
import java.util.List;

public interface RouteService {
    List<Transportation> listRoutes(Long originLocationId, Long destinationLocationId, Date date);
}
