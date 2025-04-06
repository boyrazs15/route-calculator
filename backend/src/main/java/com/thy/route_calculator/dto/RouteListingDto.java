package com.thy.route_calculator.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class RouteListingDto {
  @NotNull(message = "Origin location id is required")
  private Long originLocationId;

  @NotNull(message = "Destination location id is required")
  private Long destinationLocationId;

  @NotNull(message = "Date is required")
  private LocalDate date;
}
