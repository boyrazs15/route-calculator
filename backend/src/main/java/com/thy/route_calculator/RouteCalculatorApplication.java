package com.thy.route_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RouteCalculatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(RouteCalculatorApplication.class, args);
  }
}
