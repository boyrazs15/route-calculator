package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import com.thy.route_calculator.dto.response.RouteStepDto;
import com.thy.route_calculator.model.Transportation;

public class RouteListingMapper {
  public static RouteListingResponseDto toDto(
      Transportation beforeFlightTransfer,
      Transportation flight,
      Transportation afterFlightTransfer) {
    RouteStepDto beforeFlightTransferDto =
        new RouteStepDto(
            beforeFlightTransfer.getOriginLocation().getName(),
            beforeFlightTransfer.getDestinationLocation().getName(),
            beforeFlightTransfer.getTransportationType());
    RouteStepDto flightDto =
        new RouteStepDto(
            flight.getOriginLocation().getName(),
            flight.getDestinationLocation().getName(),
            flight.getTransportationType());
    RouteStepDto afterFlightTransferDto =
        new RouteStepDto(
            afterFlightTransfer.getOriginLocation().getName(),
            afterFlightTransfer.getDestinationLocation().getName(),
            afterFlightTransfer.getTransportationType());

    RouteListingResponseDto dto = new RouteListingResponseDto();

    dto.setDescription("via" + flight.getOriginLocation().getName());
    dto.setBeforeFlightTransfer(beforeFlightTransferDto);
    dto.setFlight(flightDto);
    dto.setAfterFlightTransfer(afterFlightTransferDto);

    return dto;
  }
}
