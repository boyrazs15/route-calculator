package com.thy.route_calculator.exception;

import org.springframework.http.HttpStatus;

public class TransportationNotFoundException extends ApiException {
  public TransportationNotFoundException(Long id) {
    super(
        "Transportation not found with id: " + id,
        ErrorCode.TRANSPORTATION_NOT_FOUND,
        HttpStatus.NOT_FOUND);
  }
}
