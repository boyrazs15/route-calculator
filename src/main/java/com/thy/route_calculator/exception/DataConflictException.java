package com.thy.route_calculator.exception;

import org.springframework.http.HttpStatus;

public class DataConflictException extends ApiException {
  public DataConflictException(String persistentClassName) {
    super(buildMessage(persistentClassName), ErrorCode.DATA_CONFLICT, HttpStatus.CONFLICT);
  }

  private static String buildMessage(String persistentClassName) {
    if (persistentClassName == null || persistentClassName.trim().isEmpty()) {
      return "Row was updated or deleted by another transaction. Please refresh and try again.";
    }
    return persistentClassName.substring(persistentClassName.lastIndexOf('.') + 1) + " row was updated or deleted by another transaction. Please refresh and try again.";
  }
}
