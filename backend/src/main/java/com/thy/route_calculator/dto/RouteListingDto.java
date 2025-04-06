package com.thy.route_calculator.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RouteListingDto {
  private Long originLocationId;
  private Long destinationLocationId;
  private LocalDate date;
}
