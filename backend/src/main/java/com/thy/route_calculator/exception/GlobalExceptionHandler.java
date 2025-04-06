package com.thy.route_calculator.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex) {
    return buildErrorResponse(ex.getErrorCode(), ex.getHttpStatus(), ex.getMessage(), ex.getData());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
    return buildErrorResponse(
        ErrorCode.BAD_REQUEST,
        HttpStatus.BAD_REQUEST,
        ErrorCode.BAD_REQUEST.getDefaultMessage(),
        errors);
  }

  @ExceptionHandler(OptimisticLockingFailureException.class)
  public ResponseEntity<ApiErrorResponse> handleOptimisticLock(
      OptimisticLockingFailureException ex) {
    String modelName = null;

    if (ex instanceof ObjectOptimisticLockingFailureException) {
      modelName = (((ObjectOptimisticLockingFailureException) ex).getPersistentClassName());
    }

    DataConflictException conflict = new DataConflictException(modelName);

    return buildErrorResponse(
        conflict.getErrorCode(),
        conflict.getHttpStatus(),
        conflict.getMessage(),
        conflict.getData());
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
