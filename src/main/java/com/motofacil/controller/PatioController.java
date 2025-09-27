package com.motofacil.controller;

import com.motofacil.entity.Coordenada;
import com.motofacil.entity.Patio;
import com.motofacil.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @PostMapping
    public Patio createPatio(@RequestBody Patio patio) {
        if (patioRepository.existsByCodigoUnico(patio.getCodigoUnico()))
            throw new RuntimeException("Já existe um pátio com esse código único.");

        // Chama ESP Python para pegar coordenadas
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + patio.getEsp32Central() + ":5001/patio-coords";
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            List<Map<String, Object>> coords = (List<Map<String, Object>>) response.getBody().get("coordenadas");

            List<Coordenada> coordenadasExtremidade = new ArrayList<>();
            for (int i = 0; i < coords.size(); i++) {
                Map<String, Object> c = coords.get(i);
                Coordenada coord = new Coordenada();
                coord.setX(Double.parseDouble(c.get("x").toString()));
                coord.setY(Double.parseDouble(c.get("y").toString()));
                coord.setOrder(i); // <-- ESSENCIAL!
                coordenadasExtremidade.add(coord);
            }
            patio.setCoordenadasExtremidade(coordenadasExtremidade);

        } catch (Exception e) {
            throw new RuntimeException("Não foi possível conectar ao simulador Python para pegar as coordenadas.");
        }

        return patioRepository.save(patio);
    }

    @GetMapping
    public List<Patio> getAllPatios() {
        return patioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patio getPatioById(@PathVariable Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
    }

    @PutMapping("/{id}")
    public Patio updatePatio(@PathVariable Long id, @RequestBody Patio patioDetails) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        patio.setNome(patioDetails.getNome());
        patio.setEndereco(patioDetails.getEndereco());
        patio.setCodigoUnico(patioDetails.getCodigoUnico());
        patio.setEsp32Central(patioDetails.getEsp32Central());
        patio.setCoordenadasExtremidade(patioDetails.getCoordenadasExtremidade());

        return patioRepository.save(patio);
    }

    @DeleteMapping("/{id}")
    public void deletePatio(@PathVariable Long id) {
        if (!patioRepository.existsById(id))
            throw new RuntimeException("Pátio não encontrado");
        patioRepository.deleteById(id);
    }
}