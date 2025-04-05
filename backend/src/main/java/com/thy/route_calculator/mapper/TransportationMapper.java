package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.LocationDto;
import com.thy.route_calculator.dto.TransportationDto;
import com.thy.route_calculator.dto.response.TransportationResponseDto;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.model.entity.Transportation;

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

  public static TransportationResponseDto toDto(Transportation entity) {
    TransportationResponseDto dto = new TransportationResponseDto();
    LocationDto originLocationDto = LocationMapper.toDto(entity.getOriginLocation());
    LocationDto destinationLocationDto = LocationMapper.toDto(entity.getDestinationLocation());
    dto.setId(entity.getId());

    dto.setOriginLocation(originLocationDto);
    dto.setDestinationLocation(destinationLocationDto);
    dto.setTransportationType(entity.getTransportationType());
    dto.setOperatingDays(entity.getOperatingDays());
    return dto;
  }
}
