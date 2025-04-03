package com.thy.route_calculator.dto;

import lombok.Data;

@Data
public class RouteListingDto {
  private String description;
  private TransferDto beforeFlightTransfer;
  private TransferDto flight;
  private TransferDto afterFlightTransfer;
}
