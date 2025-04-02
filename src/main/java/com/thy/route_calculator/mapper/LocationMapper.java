package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.LocationDto;
import com.thy.route_calculator.model.Location;

public class LocationMapper {

  public static Location toEntity(LocationDto dto) {
    Location location = new Location();
    location.setName(dto.getName());
    location.setCountry(dto.getCountry());
    location.setCity(dto.getCity());
    location.setLocationCode(dto.getLocationCode());
    return location;
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
