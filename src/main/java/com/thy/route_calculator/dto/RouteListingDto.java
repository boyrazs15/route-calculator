package com.thy.route_calculator.dto;

import java.util.Date;
import lombok.Data;

@Data
public class RouteListingDto {
  private String originLocationId;
  private String destinationLocationId;
  private Date date;
}
