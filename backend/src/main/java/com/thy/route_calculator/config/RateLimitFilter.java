package com.thy.route_calculator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thy.route_calculator.dto.response.ApiErrorResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RateLimitFilter implements Filter {

  private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

  private Bucket createNewBucket() {
    Bandwidth limit = Bandwidth.classic(30, Refill.greedy(30, Duration.ofMinutes(1)));
    return Bucket.builder().addLimit(limit).build();
  }

  private Bucket resolveBucket(String ip) {
    return cache.computeIfAbsent(ip, k -> createNewBucket());
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpReq = (HttpServletRequest) request;
    HttpServletResponse httpRes = (HttpServletResponse) response;

    String ip = httpReq.getRemoteAddr();
    Bucket bucket = resolveBucket(ip);

    if (bucket.tryConsume(1)) {
      chain.doFilter(request, response);
    } else {
      ObjectMapper mapper = new ObjectMapper();
      ApiErrorResponse error =
          ApiErrorResponse.builder()
              .errorCode("TOO_MANY_REQUESTS")
              .message("Too many requests - rate limit exceeded")
              .statusCode(HttpStatus.TOO_MANY_REQUESTS.value())
              .build();

      httpRes.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      httpRes.setContentType("application/json");
      httpRes.getWriter().write(mapper.writeValueAsString(error));
    }
  }
}
