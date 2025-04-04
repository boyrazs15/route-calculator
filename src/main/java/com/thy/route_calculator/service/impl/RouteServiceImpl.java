package com.thy.route_calculator.service.impl;

import com.thy.route_calculator.model.Transportation;
import com.thy.route_calculator.repository.LocationRepository;
import com.thy.route_calculator.repository.TransportationRepository;
import com.thy.route_calculator.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    private final LocationRepository locationRepository;
    private final TransportationRepository transportationRepository;

    @Autowired
    public RouteServiceImpl(LocationRepository locationRepository, TransportationRepository transportationRepository) {
        this.locationRepository = locationRepository;
        this.transportationRepository = transportationRepository;
    }

    @Override
    public List<Transportation> listRoutes(Long originLocationId, Long destinationLocationId, Date date) {
        /*
        1.
         - (type = flight) ve
         - (originLocationId-> city = originLocation.city) ve
         - (destinationLocationId-> city = destinationLocation.city) ve
         - (operatingDays in içinde date günü olan)
         olan transportation(lar) bul. -> transportation F olsun.

        2. BEFORE:
         - (originLocation.id = request.originLocationId) ve
         - (destinationLocation.id = F.originLocation.id) ve
         - (operatingDays in içinde date günü olan) ve
         - (type != flight)
         olan transportation(lar) bul. -> transportation B olsun.

        3. AFTER:
         - (originLocation.id = F.originLocation.id) ve
         - (destinationLocation.id = request.destinationLocationId) ve
         - (operatingDays in içinde date günü olan) ve
         - (type != flight)
         olan transportation(lar) bul. -> transportation A olsun.

        4. Kombinasyonlar :
         - FLIGHT
         - BEFORE + FLIGHT
         - FLIGHT + AFTER
         - BEFORE + FLIGHT + AFTER
         */

        return List.of();
    }
}
