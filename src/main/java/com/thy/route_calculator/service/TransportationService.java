package com.thy.route_calculator.service;

import com.thy.route_calculator.model.Transportation;
import java.util.List;

public interface TransportationService {
  Transportation save(Transportation transportation);

  Transportation findById(Long id);

  List<Transportation> findAll();

  Transportation update(Long id, Transportation transportation);

  void deleteById(Long id);
}
