package com.thy.route_calculator.config;

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
      httpRes.setStatus(429);
      httpRes.getWriter().write("Too many requests - rate limit exceeded");
    }
  }
}
