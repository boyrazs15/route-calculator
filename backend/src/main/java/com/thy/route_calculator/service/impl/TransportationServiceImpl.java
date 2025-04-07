package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.config.TransportProperties;
import com.thy.route_calculator.exception.TransportationAlreadyExistsException;
import com.thy.route_calculator.exception.TransportationNotFoundException;
import com.thy.route_calculator.exception.TransportationNotProcessableException;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.model.enums.TransportationType;
import com.thy.route_calculator.repository.TransportationRepository;
import com.thy.route_calculator.service.TransportationService;
import java.time.DayOfWeek;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransportationServiceImpl implements TransportationService {

  private final TransportationRepository transportationRepository;
  private final TransportProperties transportProperties;

  @Override
  public Transportation save(Transportation transportation) {
    if (transportation
        .getOriginLocation()
        .getId()
        .equals(transportation.getDestinationLocation().getId())) {
      log.error(
          "Transportation origin and destination cannot be the same, locId: {}",
          transportation.getOriginLocation().getId());
      throw new TransportationNotProcessableException(transportation.getOriginLocation().getId());
    }

    log.debug("Saving transportation: {}", transportation);
    try {
      return transportationRepository.save(transportation);
    } catch (DataIntegrityViolationException e) {
      log.error(
          "Transportation already exists with the same originLocationId: {}, destinationLocationId: {}, transportationType: {}",
          transportation.getOriginLocation().getId(),
          transportation.getDestinationLocation().getId(),
          transportation.getTransportationType());
      throw new TransportationAlreadyExistsException(
          transportation.getOriginLocation(),
          transportation.getDestinationLocation(),
          transportation.getTransportationType());
    }
  }

  @Override
  public Transportation findById(Long id) {
    log.debug("Finding transportation with id: {}", id);
    return transportationRepository
        .findById(id)
        .orElseThrow(
            () -> {
              log.error("No transportation found with id: {}", id);
              return new TransportationNotFoundException(id);
            });
  }

  @Override
  @Cacheable(value = "transfersCache", key = "#originCity + '-' + #destinationCity + '-' + #day")
  public List<Transportation> findAvailableFlights(
      String originCity, String destinationCity, DayOfWeek day) {
    log.debug(
        "Finding available flights between {} and {} for {}", originCity, destinationCity, day);
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
    Transportation existing = findById(id);
    log.debug("Updating transportation with id: {}", existing.getId());

    existing.setOriginLocation(updatedTransportation.getOriginLocation());
    existing.setDestinationLocation(updatedTransportation.getDestinationLocation());
    existing.setOperatingDays(updatedTransportation.getOperatingDays());
    existing.setTransportationType(updatedTransportation.getTransportationType());

    try {
      return transportationRepository.save(existing);
    } catch (OptimisticLockingFailureException ex) {
      log.error(
          "Unable to update transportation because of optimistic lock, with id: {}, version: {}",
          existing.getId(),
          existing.getVersion());
      throw ex;
    }
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting transportation with id: {}", id);
    transportationRepository.deleteById(id);
  }

  public List<TransportationType> getEnabledTransportationTypes() {
    return transportProperties.getEnabledTransportationTypes();
  }
}
