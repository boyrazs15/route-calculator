package com.thy.route_calculator.controller;

import com.thy.route_calculator.model.Transportation;
import com.thy.route_calculator.service.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class TransportationController {

    private final TransportationService transportationService;

    @Autowired
    public TransportationController(TransportationService transportationService) {
        this.transportationService = transportationService;
    }

    @PostMapping
    public ResponseEntity<Transportation> createLocation(@RequestBody Transportation transportation) {
        return ResponseEntity.ok(transportationService.save(transportation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportation> getLocation(@PathVariable Long id) {
        return transportationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Transportation> getAllLocations() {
        return transportationService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        transportationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
