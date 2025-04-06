package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.exception.FlightNotFoundException;
import com.thy.route_calculator.model.RouteResult;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.service.LocationService;
import com.thy.route_calculator.service.RouteService;
import com.thy.route_calculator.service.TransportationService;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

  private final LocationService locationService;
  private final TransportationService transportationService;

  @Override
  public List<RouteResult> listRoutes(
      Long originLocationId, Long destinationLocationId, LocalDateTime date) {
    DayOfWeek day = date.getDayOfWeek();

    Location origin = locationService.findById(originLocationId);
    Location destination = locationService.findById(destinationLocationId);

    List<RouteResult> results = new ArrayList<>();
    List<Transportation> flights =
        transportationService.findAvailableFlights(origin.getCity(), destination.getCity(), day);

    if (flights.isEmpty()) {
      log.error(
          "No flights found for origin: {} and destination: {}",
          originLocationId,
          destinationLocationId);
      throw new FlightNotFoundException(originLocationId, destinationLocationId);
    }

    log.debug("Available flight number: {}", flights.size());

    Map<Long, List<Transportation>> beforeTransfersMap = new HashMap<>();
    Map<Long, List<Transportation>> afterTransfersMap = new HashMap<>();

    for (Transportation flight : flights) {
      Location flightDeparture = flight.getOriginLocation();
      Location flightArrival = flight.getDestinationLocation();

      if (flightDeparture.equals(origin) && flightArrival.equals(destination)) {
        log.debug("There is no need for a transfer before or after flight");
        results.add(buildRoute(null, flight, null));
        continue;
      }

      List<Transportation> beforeTransfers = new ArrayList<>();
      List<Transportation> afterTransfers = new ArrayList<>();
      if (!flightDeparture.equals(origin)) {
        log.debug("Finding available transfers for before flight");
        beforeTransfers =
            beforeTransfersMap.computeIfAbsent(
                flightDeparture.getId(),
                k -> transportationService.findAvailableTransfer(origin.getId(), k, day));
      }
      if (!flightArrival.equals(destination)) {
        log.debug("Finding available transfers for after flight");
        afterTransfers =
            afterTransfersMap.computeIfAbsent(
                flightArrival.getId(),
                k -> transportationService.findAvailableTransfer(k, destination.getId(), day));
      }

      if (flightArrival.equals(destination)) {
        log.debug("There is no need for a transfer after the flight");
        for (Transportation before : beforeTransfers) {
          results.add(buildRoute(before, flight, null));
        }
        continue;
      }

      if (flightDeparture.equals(origin)) {
        log.debug("There is no need for a transfer before the flight");
        for (Transportation after : afterTransfers) {
          results.add(buildRoute(null, flight, after));
        }
        continue;
      }

      for (Transportation before : beforeTransfers) {
        for (Transportation after : afterTransfers) {
          log.debug("Building a route with before and after flight transfers");
          results.add(buildRoute(before, flight, after));
        }
      }
    }

    return results;
  }

  private RouteResult buildRoute(
      Transportation before, Transportation flight, Transportation after) {
    return RouteResult.builder()
        .beforeFlightTransfer(Optional.ofNullable(before))
        .flight(flight)
        .afterFlightTransfer(Optional.ofNullable(after))
        .build();
  }
}
