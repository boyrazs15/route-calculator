package com.thy.route_calculator.exception;

import org.springframework.http.HttpStatus;

public class TransportationNotProcessableException extends ApiException {
  public TransportationNotProcessableException(Long id) {
    super(
        "Transportation origin and destination cannot be the same, locId: : " + id,
        ErrorCode.TRANSPORTATION_NOT_PROCESSABLE,
        HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
