package com.thy.route_calculator.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {
  private String message;
  private String errorCode;
  private int statusCode;
  private Object data;
}
