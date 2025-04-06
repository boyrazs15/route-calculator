package com.thy.route_calculator.exception;

import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.model.enums.TransportationType;
import org.springframework.http.HttpStatus;

public class TransportationAlreadyExistsException extends ApiException {
  public TransportationAlreadyExistsException(
      Location origin, Location destination, TransportationType transportationType) {
    super(
        "Transportation already exists with originLocation: "
            + origin.getName()
            + ", destinationLocation: "
            + destination.getName()
            + ", transportationType: "
            + transportationType,
        ErrorCode.TRANSPORTATION_ALREADY_EXISTS,
        HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
