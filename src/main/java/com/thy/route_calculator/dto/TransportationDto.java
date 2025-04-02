package com.thy.route_calculator.dto;

import com.thy.route_calculator.model.enums.TransportationType;
import lombok.Data;

import java.util.List;

@Data
public class TransportationDto {
    private Long id;
    private Long originLocationId;
    private Long destinationLocationId;
    private TransportationType transportationType;
    private List<Integer> operatingDays;

}
