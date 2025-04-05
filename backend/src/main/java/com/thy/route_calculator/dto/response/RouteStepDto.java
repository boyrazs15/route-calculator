package com.thy.route_calculator.dto.response;

import com.thy.route_calculator.model.enums.TransportationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteStepDto {
  private String origin;
  private String destination;
  private TransportationType transportationType;
}
