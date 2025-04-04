package com.thy.route_calculator.model;

import com.thy.route_calculator.model.entity.Transportation;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResult {

    private Optional<Transportation> beforeFlightTransportation;
    private Transportation flight;
    private Optional<Transportation> afterFlightTransportation;
}
