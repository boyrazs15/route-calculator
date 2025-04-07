package com.thy.route_calculator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thy.route_calculator.dto.response.ApiErrorResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyFilter implements Filter {

  @Value("${api.key}")
  private String validApiKey;

  private static final String API_KEY_HEADER = "X-API-KEY";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpReq = (HttpServletRequest) request;
    HttpServletResponse httpRes = (HttpServletResponse) response;

    if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) { // CORS preflight istekleri için
      chain.doFilter(request, response);
      return;
    }

    String apiKey = httpReq.getHeader(API_KEY_HEADER);

    if (apiKey == null || apiKey.isEmpty()) {
      ObjectMapper mapper = new ObjectMapper();
      ApiErrorResponse error =
          ApiErrorResponse.builder()
              .errorCode("BAD_REQUEST")
              .message("Missing Header: X-API-KEY")
              .statusCode(HttpStatus.UNAUTHORIZED.value())
              .build();

      httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpRes.setContentType("application/json");
      httpRes.getWriter().write(mapper.writeValueAsString(error));
      return;
    }

    if (validApiKey.equals(apiKey)) {
      chain.doFilter(request, response);
    } else {
      ObjectMapper mapper = new ObjectMapper();
      ApiErrorResponse error =
          ApiErrorResponse.builder()
              .errorCode("UNAUTHORIZED")
              .message("Invalid API Key")
              .statusCode(HttpStatus.UNAUTHORIZED.value())
              .build();

      httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpRes.setContentType("application/json");
      httpRes.getWriter().write(mapper.writeValueAsString(error));
    }
  }
}
