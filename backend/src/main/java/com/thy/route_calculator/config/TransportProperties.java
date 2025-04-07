package com.thy.route_calculator.config;

import com.thy.route_calculator.model.enums.TransportationType;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "transport")
public class TransportProperties {

  private List<String> enabledTypes;

  public void setEnabledTypes(List<String> enabledTypes) {
    for (String type : enabledTypes) {
      if (!TransportationType.isValid(type)) {
        throw new IllegalArgumentException("Invalid transportation type in config: " + type);
      }
    }
    this.enabledTypes = enabledTypes;
  }

  public List<TransportationType> getEnabledTransportationTypes() {
    return enabledTypes.stream().map(TransportationType::valueOf).toList();
  }
}
