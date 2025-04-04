package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.exception.TransportationNotFoundException;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.repository.TransportationRepository;
import com.thy.route_calculator.service.TransportationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportationServiceImpl implements TransportationService {

  private final TransportationRepository transportationRepository;

  @Autowired
  public TransportationServiceImpl(TransportationRepository transportationRepository) {
    this.transportationRepository = transportationRepository;
  }

  @Override
  public Transportation save(Transportation transportation) {
    return transportationRepository.save(transportation);
  }

  @Override
  public Transportation findById(Long id) {
    return transportationRepository
        .findById(id)
        .orElseThrow(() -> new TransportationNotFoundException(id));
  }

  @Override
  public List<Optional<Transportation>> findAvailableFlightTransportations(
      String originLocationCity, String destinationLocationCity, LocalDateTime date) {
    return List.of();
  }

  @Override
  public List<Optional<Transportation>> findAvailableBeforeFlightTransportations(
      Long originLocationId, Transportation flightTransportation, LocalDateTime date) {
    return List.of();
  }

  @Override
  public List<Optional<Transportation>> findAvailableAfterFlightTransportations(
      Transportation flightTransportation, Long destinationLocationId, LocalDateTime date) {
    return List.of();
  }

  @Override
  public List<Transportation> findAll() {
    return transportationRepository.findAll();
  }

  @Override
  public Transportation update(Long id, Transportation updatedTransportation) {
    Transportation existing =
        transportationRepository
            .findById(id)
            .orElseThrow(() -> new TransportationNotFoundException(id));

    existing.setOriginLocation(updatedTransportation.getOriginLocation());
    existing.setDestinationLocation(updatedTransportation.getDestinationLocation());
    existing.setOperatingDays(updatedTransportation.getOperatingDays());
    existing.setTransportationType(updatedTransportation.getTransportationType());

    return transportationRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    transportationRepository.deleteById(id);
  }
}
