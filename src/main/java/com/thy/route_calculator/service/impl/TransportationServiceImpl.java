package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.exception.TransportationNotFoundException;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.model.enums.TransportationType;
import com.thy.route_calculator.repository.TransportationRepository;
import com.thy.route_calculator.service.TransportationService;

import java.time.DayOfWeek;
import java.util.List;
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
  public List<Transportation> findAvailableFlights(String originCity, String destinationCity, DayOfWeek day) {
    return transportationRepository.findByTypeAndOriginCityAndDestinationCityAndOperatingDay(
            TransportationType.FLIGHT, originCity, destinationCity, day.getValue()
    );
  }

  @Override
  public List<Transportation> findAvailableTransfer(Long originId, Long destinationId, DayOfWeek day) {
    return transportationRepository.findByTypeNotAndOriginIdAndDestinationIdAndOperatingDay(
            TransportationType.FLIGHT, originId, destinationId, day.getValue()
    );
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
