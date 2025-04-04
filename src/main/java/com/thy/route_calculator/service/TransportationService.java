package com.thy.route_calculator.service;

import com.thy.route_calculator.model.entity.Transportation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransportationService {
  Transportation save(Transportation transportation);

  Transportation findById(Long id);

  List<Optional<Transportation>> findAvailableFlightTransportations(
      String originLocationCity, String destinationLocationCity, LocalDateTime date);

  List<Optional<Transportation>> findAvailableBeforeFlightTransportations(
      Long originLocationId, Transportation flightTransportation, LocalDateTime date);

  List<Optional<Transportation>> findAvailableAfterFlightTransportations(
      Transportation flightTransportation, Long destinationLocationId, LocalDateTime date);

  List<Transportation> findAll();

  Transportation update(Long id, Transportation transportation);

  void deleteById(Long id);
}
