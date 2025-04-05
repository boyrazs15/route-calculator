package com.thy.route_calculator.exception;

import org.springframework.http.HttpStatus;

public class LocationNotFoundException extends ApiException {
  public LocationNotFoundException(Long id) {
    super("Location not found with id: " + id, ErrorCode.LOCATION_NOT_FOUND, HttpStatus.NOT_FOUND);
  }
}
