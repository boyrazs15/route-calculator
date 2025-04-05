package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import com.thy.route_calculator.dto.response.RouteStepDto;
import com.thy.route_calculator.model.RouteResult;
import com.thy.route_calculator.model.entity.Transportation;

public class RouteListingMapper {

  public static RouteListingResponseDto toDto(RouteResult routeResult) {

    RouteListingResponseDto dto = new RouteListingResponseDto();

    dto.setDescription("via " + routeResult.getFlight().getOriginLocation().getName());

    dto.setFlight(toStepDto(routeResult.getFlight()));

    dto.setBeforeFlightTransfer(
        routeResult.getBeforeFlightTransfer().map(RouteListingMapper::toStepDto).orElse(null));

    dto.setAfterFlightTransfer(
        routeResult.getAfterFlightTransfer().map(RouteListingMapper::toStepDto).orElse(null));

    return dto;
  }

  private static RouteStepDto toStepDto(Transportation transportation) {
    return new RouteStepDto(
        transportation.getOriginLocation().getName(),
        transportation.getDestinationLocation().getName(),
        transportation.getTransportationType());
  }
}
