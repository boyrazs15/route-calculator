package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.LocationDto;
import com.thy.route_calculator.model.Location;

public class LocationMapper {

  public static Location toEntity(LocationDto dto) {
    return Location.builder()
        .name(dto.getName())
        .country(dto.getCountry())
        .city(dto.getCity())
        .locationCode(dto.getLocationCode())
        .build();
  }

  public static LocationDto toDto(Location entity) {
    LocationDto dto = new LocationDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setCountry(entity.getCountry());
    dto.setCity(entity.getCity());
    dto.setLocationCode(entity.getLocationCode());
    return dto;
  }
}
