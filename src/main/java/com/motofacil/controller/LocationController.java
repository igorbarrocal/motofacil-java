package com.motofacil.controller;

import com.motofacil.dto.LocationDTO;
import com.motofacil.entity.Location;
import com.motofacil.entity.Moto;
import com.motofacil.repository.LocationRepository;
import com.motofacil.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MotoRepository motoRepository;

    @PostMapping
    public Location saveLocation(@RequestBody LocationDTO dto) {
        Moto moto = motoRepository.findById(dto.getMotoId())
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        Location location = new Location();
        location.setX(dto.getX());
        location.setY(dto.getY());
        location.setTimestamp(LocalDateTime.now());
        location.setMoto(moto);

        moto.setLocation(location);

        return locationRepository.save(location);
    }

    @GetMapping("/moto/{id}/latest")
    public Location getLatestByMoto(@PathVariable Long id) {
        return locationRepository.findTopByMotoIdOrderByTimestampDesc(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma localização encontrada para essa moto"));
    }

    @GetMapping("/moto/{id}/history")
    public List<Location> getHistoryByMoto(@PathVariable Long id) {
        return locationRepository.findByMotoIdOrderByTimestampDesc(id);
    }
}
