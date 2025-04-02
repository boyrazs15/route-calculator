package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.model.Transportation;
import com.thy.route_calculator.repository.TransportationRepository;
import com.thy.route_calculator.service.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportationRepository;

    @Autowired
    public TransportationServiceImpl(TransportationRepository transportationRepository) {
        this.transportationRepository = transportationRepository;
    }

    @Override
    public Transportation save(Transportation transportation) {
        return transportationRepository.save(transportation);
    }

    @Override
    public Optional<Transportation> findById(Long id) {
        return transportationRepository.findById(id);
    }

    @Override
    public List<Transportation> findAll() {
        return transportationRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        transportationRepository.deleteById(id);
    }
}

