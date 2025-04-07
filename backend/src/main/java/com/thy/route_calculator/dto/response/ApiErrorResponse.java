package com.thy.route_calculator.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {
  private boolean success;
  private String message;
  private String errorCode;
  private int statusCode;
  @Schema(nullable = true)
  private Object data;
}
