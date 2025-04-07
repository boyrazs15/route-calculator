package com.thy.route_calculator;

import com.thy.route_calculator.config.TransportProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(TransportProperties.class)
public class RouteCalculatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(RouteCalculatorApplication.class, args);
  }
}
