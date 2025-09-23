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
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private LocationRepository locationRepository;

    // Criar nova moto com localização inicial opcional
    @PostMapping
    public Moto createMoto(@RequestBody Moto moto) {
        if(moto.getLocation() != null) {
            Location location = moto.getLocation();
            location.setTimestamp(LocalDateTime.now());
            moto.setLocation(locationRepository.save(location));
        }
        return motoRepository.save(moto);
    }

    // Atualizar localização de uma moto
    @PutMapping("/{id}/location")
    public Moto updateMotoLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
        Moto moto = motoRepository.findById(id).orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        Location location = new Location();
        location.setX(dto.getX());
        location.setY(dto.getY());
        location.setTimestamp(LocalDateTime.now());
        location = locationRepository.save(location);

        moto.setLocation(location);
        return motoRepository.save(moto);
    }

    // Buscar localização da moto
    @GetMapping("/{id}/location")
    public Location getMotoLocation(@PathVariable Long id) {
        Moto moto = motoRepository.findById(id).orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        return moto.getLocation();
    }

    // Listar todas motos
    @GetMapping
    public List<Moto> getAllMotos() {
        return motoRepository.findAll();
    }

    // Buscar moto por id
    @GetMapping("/{id}")
    public Moto getMotoById(@PathVariable Long id) {
        return motoRepository.findById(id).orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }
}
