package com.thy.route_calculator.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thy.route_calculator.exception.FlightNotFoundException;
import com.thy.route_calculator.model.RouteResult;
import com.thy.route_calculator.model.entity.Location;
import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.model.enums.TransportationType;
import com.thy.route_calculator.service.RouteService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// https://spring.io/guides/gs/testing-web
@WebMvcTest(controllers = RouteController.class)
class RouteControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RouteService routeService;

  @Test
  void shouldReturnRoutesSuccessfully() throws Exception {
    Transportation flight = new Transportation();
    Location origin = new Location();
    origin.setName("Istanbul");
    Location destination = new Location();
    destination.setName("London");
    flight.setOriginLocation(origin);
    flight.setDestinationLocation(destination);
    flight.setTransportationType(TransportationType.FLIGHT);

    RouteResult routeResult =
        RouteResult.builder()
            .flight(flight)
            .beforeFlightTransfer(Optional.empty())
            .afterFlightTransfer(Optional.empty())
            .build();

    when(routeService.listRoutes(eq(1L), eq(2L), any(LocalDateTime.class)))
        .thenReturn(List.of(routeResult));

    mockMvc
        .perform(
            get("/api/routes")
                .param("originLocationId", "1")
                .param("destinationLocationId", "2")
                .param("date", "2025-04-07"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].flight.origin").value("Istanbul"))
        .andExpect(jsonPath("$[0].flight.destination").value("London"))
        .andExpect(jsonPath("$[0].flight.transportationType").value("FLIGHT"));
  }

  @Test
  void shouldReturn404WhenFlightNotFoundExceptionThrown() throws Exception {
    when(routeService.listRoutes(any(), any(), any()))
        .thenThrow(new FlightNotFoundException(1L, 2L));

    mockMvc
        .perform(
            get("/api/routes")
                .param("originLocationId", "1")
                .param("destinationLocationId", "2")
                .param("date", "2025-04-07")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            jsonPath("$.message")
                .value(
                    "Flight not found with origin location id: 1 and destination location id: 2"))
        .andExpect(jsonPath("$.errorCode").value("FLG-404"))
        .andExpect(jsonPath("$.statusCode").value(404));
  }
}
