package com.thy.route_calculator.dto;

import com.thy.route_calculator.model.enums.TransportationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class TransportationDto {
  private Long id;

  @NotNull(message = "Origin location is required")
  private Long originLocationId;

  @NotNull(message = "Destination location is required")
  private Long destinationLocationId;

  @NotNull(message = "Transportation type is required")
  private TransportationType transportationType;

  @NotNull(message = "Operating days are required")
  @Size(min = 1, message = "At least one operating day must be selected")
  private List<Integer> operatingDays;
}
