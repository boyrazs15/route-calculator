package com.thy.route_calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.thy.route_calculator.dto.response.RouteListingResponseDto;
import com.thy.route_calculator.exception.ErrorCode;
import com.thy.route_calculator.model.enums.TransportationType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(
    scripts = {"/location.sql", "/transportation.sql", "/transportation_operating_days.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class RouteControllerIntegrationTest {

  @LocalServerPort private int port;

  private final RestTemplate restTemplate = new RestTemplate();

  @Container
  static PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("routedb")
          .withUsername("thy_user")
          .withPassword("123456");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

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
  void shouldReturnValidRoutesForOnlyFlightRoute() {
    // only flight
    Long validOrigin = 6L; // Istanbul Airport
    Long validDestination = 5L; // London Heathrow Airport
    LocalDate validDate = LocalDate.of(2025, 4, 7);
    String query = buildQuery(validOrigin, validDestination, validDate);

    ResponseEntity<List<RouteListingResponseDto>> response =
        restTemplate.exchange(
            buildUrl(query), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<RouteListingResponseDto> routes = response.getBody();
    assertThat(routes).isNotEmpty();
    assertThat(routes.size()).isEqualTo(1);

    RouteListingResponseDto flight = routes.get(0);
    assertThat(flight.getAfterFlightTransfer()).isNull();
    assertThat(flight.getBeforeFlightTransfer()).isNull();
    assertThat(flight.getFlight().getTransportationType()).isEqualTo(TransportationType.FLIGHT);
  }

  @Test
  void shouldReturnValidRoutesForAMultipleFlightRouteOnOperatingDate() {
    // only flight
    Long validOrigin = 4L; // Taksim Square
    Long validDestination = 8L; // Wembley Stadium
    LocalDate operatingDate = LocalDate.of(2025, 4, 7); // Monday
    String query = buildQuery(validOrigin, validDestination, operatingDate);

    ResponseEntity<List<RouteListingResponseDto>> response =
        restTemplate.exchange(
            buildUrl(query), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<RouteListingResponseDto> routes = response.getBody();
    assertThat(routes).isNotEmpty();
    assertThat(routes.size()).isEqualTo(6);
    assertThat(routes.toString())
        .contains(
            TransportationType.BUS.toString(),
            TransportationType.FLIGHT.toString(),
            TransportationType.UBER.toString(),
            TransportationType.SUBWAY.toString());

    RouteListingResponseDto firstRoute = routes.get(2);
    assertThat(firstRoute.getBeforeFlightTransfer()).isNotNull();
    assertThat(firstRoute.getBeforeFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.SUBWAY);
    assertThat(firstRoute.getFlight().getOrigin()).isEqualTo("İstanbul Havalimanı");
    assertThat(firstRoute.getAfterFlightTransfer().getOrigin())
        .isEqualTo("London Heathrow Airport");
    assertThat(firstRoute.getAfterFlightTransfer().getDestination()).isEqualTo("Wembley Stadium");
    assertThat(firstRoute.getAfterFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.BUS);

    RouteListingResponseDto forthRoute = routes.get(5);
    assertThat(forthRoute.getAfterFlightTransfer()).isNotNull();
    assertThat(forthRoute.getBeforeFlightTransfer()).isNotNull();
    assertThat(forthRoute.getFlight().getOrigin()).isEqualTo("Sabiha Gökçen Havalimanı");
    assertThat(forthRoute.getBeforeFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.SUBWAY);
    assertThat(forthRoute.getAfterFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.UBER);
  }

  @Test
  void shouldReturnValidRoutesForAMultipleFlightRouteOnNonOperatingDate() {
    // only flight
    Long validOrigin = 4L; // Taksim Square
    Long validDestination = 8L; // Wembley Stadium
    LocalDate nonOperatingDate = LocalDate.of(2025, 4, 13); // Sunday
    String query = buildQuery(validOrigin, validDestination, nonOperatingDate);

    ResponseEntity<List<RouteListingResponseDto>> response =
        restTemplate.exchange(
            buildUrl(query), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<RouteListingResponseDto> routes = response.getBody();
    assertThat(routes).isNotEmpty();
    assertThat(routes.size()).isEqualTo(3);
    assertThat(routes.toString())
        .contains(
            TransportationType.FLIGHT.toString(),
            TransportationType.UBER.toString(),
            TransportationType.BUS.toString(),
            TransportationType.SUBWAY.toString());

    for (RouteListingResponseDto route : routes) {
      assertThat(route.getAfterFlightTransfer().toString())
          .doesNotContain(TransportationType.BUS.toString()); // non-operating day
    }
    RouteListingResponseDto firstRoute = routes.get(0);
    assertThat(firstRoute.getBeforeFlightTransfer()).isNotNull();
    assertThat(firstRoute.getBeforeFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.BUS);
    assertThat(firstRoute.getFlight().getOrigin()).isEqualTo("İstanbul Havalimanı");
    assertThat(firstRoute.getAfterFlightTransfer().getOrigin())
        .isEqualTo("London Heathrow Airport");
    assertThat(firstRoute.getAfterFlightTransfer().getDestination()).isEqualTo("Wembley Stadium");
    assertThat(firstRoute.getAfterFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.UBER);

    RouteListingResponseDto forthRoute = routes.get(2);
    assertThat(forthRoute.getAfterFlightTransfer()).isNotNull();
    assertThat(forthRoute.getBeforeFlightTransfer()).isNotNull();
    assertThat(forthRoute.getFlight().getOrigin()).isEqualTo("Sabiha Gökçen Havalimanı");
    assertThat(forthRoute.getBeforeFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.SUBWAY);
    assertThat(forthRoute.getAfterFlightTransfer().getTransportationType())
        .isEqualTo(TransportationType.UBER);
  }

  @Test
  void shouldReturn404WhenNoLocationFound() {
    Long invalidOrigin = 999L;
    Long validDestination = 8L; // Wembley Stadium
    LocalDate invalidDate = LocalDate.of(2025, 4, 7);
    String query = buildQuery(invalidOrigin, validDestination, invalidDate);

    try {
      restTemplate.exchange(buildUrl(query), HttpMethod.GET, null, String.class);
      fail("Expected HttpClientErrorException.NotFound");
    } catch (HttpClientErrorException.NotFound ex) {
      assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(ex.getResponseBodyAsString()).contains(ErrorCode.LOCATION_NOT_FOUND.getCode());
      assertThat(ex.getResponseBodyAsString()).contains("Location not found with id: 999");
    }
  }

  @Test
  void shouldReturn404WhenNoFlightFound() {
    Long origin = 5L; // London Heathrow Airport
    Long destination = 8L; // Wembley Stadium
    LocalDate invalidDate = LocalDate.of(2025, 4, 7);
    String query = buildQuery(origin, destination, invalidDate);

    try {
      restTemplate.exchange(buildUrl(query), HttpMethod.GET, null, String.class);
      fail("Expected HttpClientErrorException.NotFound");
    } catch (HttpClientErrorException.NotFound ex) {
      assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(ex.getResponseBodyAsString()).contains(ErrorCode.FLIGHT_NOT_FOUND.getCode());
    }
  }

  @Test
  void shouldReturn400WhenMissingParams() {
    String query = "?originLocationId=1&destinationLocationId=2";

    try {
      restTemplate.exchange(buildUrl(query), HttpMethod.GET, null, String.class);
      fail("Expected HttpClientErrorException.BadRequest");
    } catch (HttpClientErrorException.BadRequest ex) {
      assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
      assertThat(ex.getResponseBodyAsString()).contains(ErrorCode.BAD_REQUEST.getCode());
      assertThat(ex.getResponseBodyAsString()).contains("Date is required");
    }
  }
}
