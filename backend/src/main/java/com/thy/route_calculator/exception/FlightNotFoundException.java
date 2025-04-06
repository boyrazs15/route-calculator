package com.thy.route_calculator.exception;

import org.springframework.http.HttpStatus;

public class FlightNotFoundException extends ApiException {
  public FlightNotFoundException(Long originId, Long destinationId) {
    super(
        "Flight not found with origin location id: "
            + originId
            + " and destination location id: "
            + destinationId,
        ErrorCode.FLIGHT_NOT_FOUND,
        HttpStatus.NOT_FOUND);
  }
}
