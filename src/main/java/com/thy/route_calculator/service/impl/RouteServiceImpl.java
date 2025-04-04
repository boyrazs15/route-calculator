package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.model.RouteResult;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.service.LocationService;
import com.thy.route_calculator.service.RouteService;
import com.thy.route_calculator.service.TransportationService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

  private final LocationService locationService;
  private final TransportationService transportationService;

  @Autowired
  public RouteServiceImpl(
      LocationService locationService, TransportationService transportationService) {
    this.locationService = locationService;
    this.transportationService = transportationService;
  }

  @Override
  public List<RouteResult> listRoutes(
      Long originLocationId, Long destinationLocationId, LocalDateTime date) {
    List<RouteResult> routes = new ArrayList<>();

    Location origin = locationService.findById(originLocationId);
    Location destination = locationService.findById(destinationLocationId);

    List<Optional<Transportation>> flightOptions =
        transportationService.findAvailableFlightTransportations(
            origin.getCity(), destination.getCity(), date);

    for (Optional<Transportation> optionalFlight : flightOptions) {
      if (optionalFlight.isEmpty()) continue;
      Transportation flight = optionalFlight.get();

      routes.add(
          RouteResult.builder()
              .flight(flight)
              .beforeFlightTransportation(Optional.empty())
              .afterFlightTransportation(Optional.empty())
              .build());

      List<Optional<Transportation>> beforeOptions =
          transportationService.findAvailableBeforeFlightTransportations(
              originLocationId, flight, date);

      for (Optional<Transportation> optionalBefore : beforeOptions) {
        if (optionalBefore.isEmpty()) continue;
        Transportation before = optionalBefore.get();

        routes.add(
            RouteResult.builder()
                .beforeFlightTransportation(Optional.of(before))
                .flight(flight)
                .afterFlightTransportation(Optional.empty())
                .build());

        List<Optional<Transportation>> afterOptions =
            transportationService.findAvailableAfterFlightTransportations(
                flight, destinationLocationId, date);

        for (Optional<Transportation> optionalAfter : afterOptions) {
          if (optionalAfter.isEmpty()) continue;
          Transportation after = optionalAfter.get();

          routes.add(
              RouteResult.builder()
                  .beforeFlightTransportation(Optional.of(before))
                  .flight(flight)
                  .afterFlightTransportation(Optional.of(after))
                  .build());
        }
      }

      List<Optional<Transportation>> afterOptions =
          transportationService.findAvailableAfterFlightTransportations(
              flight, destinationLocationId, date);

      for (Optional<Transportation> optionalAfter : afterOptions) {
        if (optionalAfter.isEmpty()) continue;
        Transportation after = optionalAfter.get();

        routes.add(
            RouteResult.builder()
                .beforeFlightTransportation(Optional.empty())
                .flight(flight)
                .afterFlightTransportation(Optional.of(after))
                .build());
      }
    }

    return routes;
  }
}
