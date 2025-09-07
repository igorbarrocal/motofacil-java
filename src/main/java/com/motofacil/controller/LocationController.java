package com.motofacil.controller;

import com.motofacil.dto.LocationDTO;
import com.motofacil.entity.Location;
import com.motofacil.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping
    public Location saveLocation(@RequestBody LocationDTO dto) {
        Location location = new Location();
        location.setX(dto.getX());
        location.setY(dto.getY());
        location.setTimestamp(LocalDateTime.now());
        return locationRepository.save(location);
    }

    @GetMapping("/latest")
    public Location getLatestLocation() {
        return locationRepository.findTopByOrderByTimestampDesc();
    }
}
