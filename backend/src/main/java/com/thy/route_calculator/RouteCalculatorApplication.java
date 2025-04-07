package com.thy.route_calculator;

import com.thy.route_calculator.config.ApiKeyFilter;
import com.thy.route_calculator.config.TransportProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(TransportProperties.class)
public class RouteCalculatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(RouteCalculatorApplication.class, args);
  }

  @Bean
  public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration(ApiKeyFilter filter) {
    FilterRegistrationBean<ApiKeyFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(filter);
    registration.addUrlPatterns("/api/*");
    registration.setOrder(1);
    return registration;
  }
}
