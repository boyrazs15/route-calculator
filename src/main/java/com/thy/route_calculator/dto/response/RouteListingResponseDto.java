package com.thy.route_calculator.dto.response;

import lombok.Data;

@Data
public class RouteListingResponseDto {
  private String description;
  private TransferDto beforeFlightTransfer;
  private TransferDto flight;
  private TransferDto afterFlightTransfer;
}
