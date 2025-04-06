package com.thy.route_calculator.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  INTERNAL_ERROR("ERR-000", "Internal server error"),
  BAD_REQUEST("ERR-400", "Bad Request"),
  LOCATION_NOT_FOUND("LOC-404", "Location not found"),
  LOCATION_ALREADY_EXISTS("LOC-409", "Location already exists"),
  TRANSPORTATION_NOT_FOUND("TRN-404", "Transportation not found"),
  TRANSPORTATION_NOT_PROCESSABLE("TRN-422", "Transportation not processable"),
  TRANSPORTATION_ALREADY_EXISTS("TRN-409", "Transportation already exists"),
  FLIGHT_NOT_FOUND("FLG-404", "Flight not found"),
  DATA_CONFLICT(
      "DB-409",
      "Data conflict error: It has been modified by another transaction. Please refresh and try again.");

  private final String code;
  private final String defaultMessage;

  ErrorCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }
}
