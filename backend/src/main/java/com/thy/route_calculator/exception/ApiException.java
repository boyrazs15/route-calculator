package com.thy.route_calculator.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException {
  private final ErrorCode errorCode;
  private final HttpStatus httpStatus;
  private final Object data;

  protected ApiException(String message, ErrorCode errorCode, HttpStatus httpStatus, Object data) {
    super(message);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
    this.data = data;
  }

  protected ApiException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
    this(message, errorCode, httpStatus, null);
  }
}
