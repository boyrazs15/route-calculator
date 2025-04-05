package com.thy.route_calculator.model;

import com.thy.route_calculator.model.entity.Transportation;
import java.util.Optional;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResult {

  private Optional<Transportation> beforeFlightTransfer;
  private Transportation flight;
  private Optional<Transportation> afterFlightTransfer;
}
