package com.thy.route_calculator.dto.response;

import com.thy.route_calculator.dto.LocationDto;
import com.thy.route_calculator.model.enums.TransportationType;
import java.util.List;
import lombok.Data;

@Data
public class TransportationResponseDto {
  private Long id;
  private LocationDto originLocation;
  private LocationDto destinationLocation;
  private TransportationType transportationType;
  private List<Integer> operatingDays;
}
