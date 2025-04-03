package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import com.thy.route_calculator.dto.response.TransferDto;
import com.thy.route_calculator.model.Transportation;

public class RouteListingMapper {
  public static RouteListingResponseDto toDto(
      Transportation beforeFlightTransfer,
      Transportation flight,
      Transportation afterFlightTransfer) {
    TransferDto beforeFlightTransferDto =
        new TransferDto(
            beforeFlightTransfer.getOriginLocation().getName(),
            beforeFlightTransfer.getDestinationLocation().getName(),
            beforeFlightTransfer.getTransportationType());
    TransferDto flightDto =
        new TransferDto(
            flight.getOriginLocation().getName(),
            flight.getDestinationLocation().getName(),
            flight.getTransportationType());
    TransferDto afterFlightTransferDto =
        new TransferDto(
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
