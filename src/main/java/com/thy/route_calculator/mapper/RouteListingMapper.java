package com.thy.route_calculator.mapper;

import com.thy.route_calculator.dto.RouteListingDto;
import com.thy.route_calculator.dto.TransferDto;
import com.thy.route_calculator.model.Transportation;

public class RouteListingMapper {
  public static RouteListingDto toDto(
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

    RouteListingDto dto = new RouteListingDto();

    dto.setDescription("via" + flight.getOriginLocation().getName());
    dto.setBeforeFlightTransfer(beforeFlightTransferDto);
    dto.setFlight(flightDto);
    dto.setAfterFlightTransfer(afterFlightTransferDto);

    return dto;
  }
}
