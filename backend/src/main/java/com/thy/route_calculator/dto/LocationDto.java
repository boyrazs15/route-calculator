package com.thy.route_calculator.dto;

import lombok.Data;

@Data
public class LocationDto {
  private Long id;
  private String name;
  private String country;
  private String city;
  private String locationCode;
}
