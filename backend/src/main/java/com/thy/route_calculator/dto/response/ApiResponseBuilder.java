package com.thy.route_calculator.dto.response;

import com.thy.route_calculator.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseBuilder {

  public static <T> ResponseEntity<ApiSuccessResponse<T>> success(T data) {
    ApiSuccessResponse<T> success =
        ApiSuccessResponse.<T>builder().success(true).data(data).build();
    return new ResponseEntity<>(success, HttpStatus.OK);
  }

  public static ResponseEntity<ApiErrorResponse> error(
      ErrorCode code, HttpStatus status, String msg, Object data) {
    ApiErrorResponse error =
        ApiErrorResponse.builder()
            .success(false)
            .message(msg != null ? msg : code.getDefaultMessage())
            .errorCode(String.valueOf(code))
            .statusCode(status.value())
            .data(data)
            .build();

    return new ResponseEntity<>(error, status);
  }
}
