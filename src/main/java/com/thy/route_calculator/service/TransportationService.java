package com.thy.route_calculator.service;

import com.thy.route_calculator.model.Transportation;
import java.util.List;
import java.util.Optional;

public interface TransportationService {
  Transportation save(Transportation transportation);

  Optional<Transportation> findById(Long id);

  List<Transportation> findAll();

  Transportation update(Long id, Transportation transportation);

  void deleteById(Long id);
}
