package com.thy.route_calculator.model.enums;

public enum TransportationType {
  FLIGHT,
  BUS,
  SUBWAY,
  UBER;

  public static boolean isValid(String value) {
    try {
      TransportationType.valueOf(value);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
