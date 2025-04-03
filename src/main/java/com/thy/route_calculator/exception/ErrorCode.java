package com.thy.route_calculator.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  INTERNAL_ERROR("ERR-000", "Internal server error"),
  LOCATION_NOT_FOUND("LOC-404", "Location not found"),
  TRANSPORTATION_NOT_FOUND("TRN-404", "Transportation not found"),
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
