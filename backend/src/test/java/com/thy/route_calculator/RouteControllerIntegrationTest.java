package com.thy.route_calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import com.thy.route_calculator.exception.ErrorCode;
import com.thy.route_calculator.model.enums.TransportationType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouteControllerIntegrationTest {

  @LocalServerPort private int port;

  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired private ObjectMapper objectMapper;

  private String buildQuery(Long origin, Long destination, LocalDate date) {
    return "?originLocationId="
        + origin
        + "&destinationLocationId="
        + destination
        + "&date="
        + date;
  }

  private String buildUrl(String query) {
    return "http://localhost:" + port + "/api/routes" + query;
  }

  @Test
  void shouldReturnValidRoutesForCorrectInput() {
    String query = "?originLocationId=1&destinationLocationId=2&date=2025-04-07";

    // Act
    ResponseEntity<List<RouteListingResponseDto>> response =
        restTemplate.exchange(
            buildUrl(query), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().size()).isGreaterThan(0);

    RouteListingResponseDto first = response.getBody().get(0);
    assertThat(first.getFlight().getTransportationType()).isEqualTo(TransportationType.FLIGHT);
  }

  @Test
  void shouldReturn404WhenNoLocationFound() {
    Long invalidOrigin = 999L;
    Long validDestination = 8L;
    LocalDate invalidDate = LocalDate.of(2025, 4, 7);
    String query = buildQuery(invalidOrigin, validDestination, invalidDate);

    // Act
    try {
      // Act
      restTemplate.exchange(buildUrl(query), HttpMethod.GET, null, String.class);
      fail("Expected HttpClientErrorException.NotFound");
    } catch (HttpClientErrorException.NotFound ex) {
      // Assert
      assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(ex.getResponseBodyAsString()).contains(ErrorCode.LOCATION_NOT_FOUND.getCode());
      assertThat(ex.getResponseBodyAsString()).contains("Location not found with id: 999");
    }
  }

  @Test
  void shouldReturn404WhenNoFlightFound() {
    Long origin = 5L;
    Long destination = 8L;
    LocalDate invalidDate = LocalDate.of(2025, 4, 7);
    String query = buildQuery(origin, destination, invalidDate);

    try {
      // Act
      restTemplate.exchange(buildUrl(query), HttpMethod.GET, null, String.class);
      fail("Expected HttpClientErrorException.NotFound");
    } catch (HttpClientErrorException.NotFound ex) {
      // Assert
      assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(ex.getResponseBodyAsString()).contains(ErrorCode.FLIGHT_NOT_FOUND.getCode());
    }
  }

  @Test
  void shouldReturn400WhenMissingParams() {
    String query = "?originLocationId=1&destinationLocationId=2";

    try {
      // Act
      restTemplate.exchange(buildUrl(query), HttpMethod.GET, null, String.class);
      fail("Expected HttpClientErrorException.BadRequest");
    } catch (HttpClientErrorException.BadRequest ex) {
      // Assert
      assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertThat(ex.getResponseBodyAsString()).contains(ErrorCode.BAD_REQUEST.getCode());
      assertThat(ex.getResponseBodyAsString()).contains("Date is required");
    }
  }
}
