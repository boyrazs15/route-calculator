package com.thy.route_calculator.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationDto {
  private Long id;

  @NotEmpty(message = "Name is required")
  @Size(max = 100, message = "Name cannot be longer than 100 characters")
  private String name;

  @NotEmpty(message = "Country is required")
  private String country;

  @NotEmpty(message = "City is required")
  private String city;

  @NotEmpty(message = "Location code is required.")
  @Size(max = 5, message = "Location code cannot be longer than 5 characters")
  private String locationCode;
}
