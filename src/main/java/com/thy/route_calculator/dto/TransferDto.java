package com.thy.route_calculator.dto;

import com.thy.route_calculator.model.enums.TransportationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferDto {
  private String origin;
  private String destination;
  private TransportationType transportationType;
}
