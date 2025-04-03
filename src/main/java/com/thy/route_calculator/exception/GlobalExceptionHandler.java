package com.thy.route_calculator.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex) {
    return buildErrorResponse(ex.getErrorCode(), ex.getHttpStatus(), ex.getMessage(), ex.getData());
  }

  @ExceptionHandler(OptimisticLockingFailureException.class)
  public ResponseEntity<ApiErrorResponse> handleOptimisticLock(
      OptimisticLockingFailureException ex) {
    return buildErrorResponse(ErrorCode.DATA_CONFLICT, HttpStatus.CONFLICT, ex.getMessage(), null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
    return buildErrorResponse(
        ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
  }

  private ResponseEntity<ApiErrorResponse> buildErrorResponse(
      ErrorCode code, HttpStatus status, String msg, Object data) {
    ApiErrorResponse error =
        ApiErrorResponse.builder()
            .message(msg != null ? msg : code.getDefaultMessage())
            .errorCode(code.getCode())
            .statusCode(status.value())
            .data(data)
            .build();

    return new ResponseEntity<>(error, status);
  }
}
