package com.motofacil.controller;

import com.motofacil.dto.LocationDTO;
import com.motofacil.entity.Location;
import com.motofacil.entity.Moto;
import com.motofacil.entity.Patio;
import com.motofacil.repository.LocationRepository;
import com.motofacil.repository.MotoRepository;
import com.motofacil.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PatioRepository patioRepository;

    @PostMapping
    public Moto createMoto(@RequestBody Moto moto) {
        if (moto.getPatio() == null || moto.getPatio().getId() == null)
            throw new RuntimeException("Moto precisa ser vinculada a um pátio válido.");

        Patio patio = patioRepository.findById(moto.getPatio().getId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado."));
        moto.setPatio(patio);
        moto.setStatus("pendente");
        return motoRepository.save(moto);
    }

    @PutMapping("/{id}/location")
    public Moto updateMotoLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        Long patioId = dto.getPatioId() != null ? dto.getPatioId()
                : (moto.getPatio() != null ? moto.getPatio().getId() : null);
        if (patioId == null)
            throw new RuntimeException("É necessário informar um pátio para atualizar a localização.");

        Patio patio = patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + patio.getEsp32Central() + ":5001/simulate";
        Map<String, Object> payload = Map.of("id", moto.getId(), "x", dto.getX(), "y", dto.getY());
        ResponseEntity<Map> response = restTemplate.postForEntity(url, payload, Map.class);

        Map pythonResp = response.getBody();
        Map<String, Object> localizacaoSorteada = (Map<String, Object>) pythonResp.get("localizacaoSorteada");
        float x = Float.parseFloat(localizacaoSorteada.get("x").toString());
        float y = Float.parseFloat(localizacaoSorteada.get("y").toString());

        Location location = new Location();
        location.setX(x);
        location.setY(y);
        location.setTimestamp(LocalDateTime.now());
        location.setMoto(moto);
        location.setPatio(patio);
        location.setTag("patio");

        locationRepository.save(location);

        moto.setStatus("patio");
        moto.setPatio(patio);
        motoRepository.save(moto);

        return moto;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Moto> updateMotoStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        String novoStatus = body.get("status");
        moto.setStatus(novoStatus);

        if ("mecanica".equalsIgnoreCase(novoStatus)) {
            moto.setPatio(null);
        } else if ("patio".equalsIgnoreCase(novoStatus)) {
            String patioIdStr = body.get("patioId");
            Long patioId = patioIdStr != null ? Long.valueOf(patioIdStr) : null;
            if (patioId == null)
                throw new RuntimeException("Para voltar ao pátio, informe o patioId");
            Patio patio = patioRepository.findById(patioId)
                    .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
            moto.setPatio(patio);
        }

        motoRepository.save(moto);
        return ResponseEntity.ok(moto);
    }

    @GetMapping("/{id}/location")
    public Location getMotoLocation(@PathVariable Long id) {
        return locationRepository.findTopByMotoIdOrderByTimestampDesc(id).orElse(null);
    }

    @GetMapping("/{id}/history")
    public List<Location> getMotoHistory(@PathVariable Long id) {
        return locationRepository.findByMotoIdOrderByTimestampDesc(id);
    }

    @GetMapping
    public List<Moto> getAllMotos() {
        return motoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Moto getMotoById(@PathVariable Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }

    @GetMapping("/patio/{patioId}")
    public List<Moto> getMotosByPatio(@PathVariable Long patioId) {
        Patio patio = patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
        return motoRepository.findByPatio(patio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoto(@PathVariable Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        motoRepository.delete(moto);
        return ResponseEntity.noContent().build();
    }
}
