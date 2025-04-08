package com.thy.route_calculator.exception;

import com.thy.route_calculator.model.enums.TransportationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransportationAlreadyExistsData {
  private String originLocation;
  private String destinationLocation;
  private TransportationType transportationType;
}
