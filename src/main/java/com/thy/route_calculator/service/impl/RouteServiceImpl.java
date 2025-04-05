package com.thy.route_calculator.service.impl;

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
import org.springframework.stereotype.Service;

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

    Map<Long, List<Transportation>> beforeTransfersMap = new HashMap<>();
    /*
    {key: 2, value: { origin: 1, destination: 2, type: UBER, operatingDays: [] } }
     */
    Map<Long, List<Transportation>> afterTransfersMap = new HashMap<>();

    for (Transportation flight : flights) {
      Location flightDeparture = flight.getOriginLocation();
      Location flightArrival = flight.getDestinationLocation();

      if (flightDeparture.equals(origin) && flightArrival.equals(destination)) {
        results.add(buildRoute(null, flight, null));
        continue;
      }

      List<Transportation> beforeTransfers = new ArrayList<>();
      List<Transportation> afterTransfers = new ArrayList<>();
      if (!flightDeparture.equals(origin)) {
        beforeTransfers =
            beforeTransfersMap.computeIfAbsent(
                flightDeparture.getId(),
                k -> transportationService.findAvailableTransfer(origin.getId(), k, day));
      }
      if (!flightArrival.equals(destination)) {
        afterTransfers =
            afterTransfersMap.computeIfAbsent(
                flightArrival.getId(),
                k -> transportationService.findAvailableTransfer(k, destination.getId(), day));
      }

      if (flightArrival.equals(destination)) {
        for (Transportation before : beforeTransfers) {
          results.add(buildRoute(before, flight, null));
        }
        continue;
      }
      if (flightDeparture.equals(origin)) {
        for (Transportation after : afterTransfers) {
          results.add(buildRoute(null, flight, after));
        }
        continue;
      }
      for (Transportation before : beforeTransfers) {

        for (Transportation after : afterTransfers) {
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
