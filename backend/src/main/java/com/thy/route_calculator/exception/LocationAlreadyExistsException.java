package com.thy.route_calculator.exception;

import org.springframework.http.HttpStatus;

public class LocationAlreadyExistsException extends ApiException {
  public LocationAlreadyExistsException(String locationCode) {
    super(
        "Location already exists with locationCode: " + locationCode,
        ErrorCode.LOCATION_ALREADY_EXISTS,
        HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
