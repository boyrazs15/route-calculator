package com.thy.route_calculator.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouteListingDto {
  private Long originLocationId;
  private Long destinationLocationId;
  private LocalDateTime date;
}
