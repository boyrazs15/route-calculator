package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.TransportationDto;
import com.thy.route_calculator.model.Location;
import com.thy.route_calculator.model.Transportation;

public class TransportationMapper {

  public static Transportation toEntity(
      TransportationDto dto, Location origin, Location destination) {
    return Transportation.builder()
        .originLocation(origin)
        .destinationLocation(destination)
        .transportationType(dto.getTransportationType())
        .operatingDays(dto.getOperatingDays())
        .build();
  }

  public static TransportationDto toDto(Transportation entity) {
    TransportationDto dto = new TransportationDto();
    dto.setId(entity.getId());
    dto.setOriginLocationId(entity.getOriginLocation().getId());
    dto.setDestinationLocationId(entity.getDestinationLocation().getId());
    dto.setTransportationType(entity.getTransportationType());
    dto.setOperatingDays(entity.getOperatingDays());
    return dto;
  }
}
