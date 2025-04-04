package com.thy.route_calculator.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RouteListingDto {
  private String originLocationId;
  private String destinationLocationId;
  private LocalDateTime date;
}
