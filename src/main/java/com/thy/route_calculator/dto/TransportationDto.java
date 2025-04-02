package com.thy.route_calculator.dto;

import com.thy.route_calculator.model.enums.TransportationType;
import java.util.List;
import lombok.Data;

@Data
public class TransportationDto {
  private Long id;
  private Long originLocationId;
  private Long destinationLocationId;
  private TransportationType transportationType;
  private List<Integer> operatingDays;
}
