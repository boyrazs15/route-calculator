package com.thy.route_calculator.dto.response;

import lombok.Data;

@Data
public class RouteListingResponseDto {
  private String description;
  private RouteStepDto beforeFlightTransfer;
  private RouteStepDto flight;
  private RouteStepDto afterFlightTransfer;
}
