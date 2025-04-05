package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.exception.TransportationNotFoundException;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.model.enums.TransportationType;
import com.thy.route_calculator.repository.TransportationRepository;
import com.thy.route_calculator.service.TransportationService;
import java.time.DayOfWeek;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransportationServiceImpl implements TransportationService {

  private final TransportationRepository transportationRepository;

  @Autowired
  public TransportationServiceImpl(TransportationRepository transportationRepository) {
    this.transportationRepository = transportationRepository;
  }

  @Override
  public Transportation save(Transportation transportation) {
    log.debug("Saving transportation: {}", transportation);
    return transportationRepository.save(transportation);
  }

  @Override
  public Transportation findById(Long id) {
    log.debug("Finding transportation with id: {}", id);
    return transportationRepository
        .findById(id)
        .orElseThrow(() -> new TransportationNotFoundException(id));
  }

  @Override
  @Cacheable(value = "transfersCache", key = "#originCity + '-' + #destinationCity + '-' + #day")
  public List<Transportation> findAvailableFlights(
      String originCity, String destinationCity, DayOfWeek day) {
    log.debug("Finding available flights between {} and {} for {}", originCity, destinationCity, day);
    return transportationRepository.findByTypeAndOriginCityAndDestinationCityAndOperatingDay(
        TransportationType.FLIGHT, originCity, destinationCity, day.getValue());
  }

  @Override
  @Cacheable(value = "transfersCache", key = "#originId + '-' + #destinationId + '-' + #day")
  public List<Transportation> findAvailableTransfer(
      Long originId, Long destinationId, DayOfWeek day) {
    log.debug("Finding available transfer between {} and {} for {}", originId, destinationId, day);
    return transportationRepository.findByTypeNotAndOriginIdAndDestinationIdAndOperatingDay(
        TransportationType.FLIGHT, originId, destinationId, day.getValue());
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
    log.debug("Updating transportation with id: {}", existing.getId());

    existing.setOriginLocation(updatedTransportation.getOriginLocation());
    existing.setDestinationLocation(updatedTransportation.getDestinationLocation());
    existing.setOperatingDays(updatedTransportation.getOperatingDays());
    existing.setTransportationType(updatedTransportation.getTransportationType());

    return transportationRepository.save(existing);
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting transportation with id: {}", id);
    transportationRepository.deleteById(id);
  }
}
