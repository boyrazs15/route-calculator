package com.thy.route_calculator.service;

import com.thy.route_calculator.model.entity.Transportation;

import java.time.DayOfWeek;
import java.util.List;

public interface TransportationService {
  Transportation save(Transportation transportation);

  Transportation findById(Long id);

  List<Transportation> findAvailableFlights(
      String originLocationCity, String destinationLocationCity, DayOfWeek day);

  List<Transportation> findAvailableTransfer(
      Long originLocationId, Long destinationLocationId, DayOfWeek day);

  List<Transportation> findAll();

  Transportation update(Long id, Transportation transportation);

  void deleteById(Long id);
}
