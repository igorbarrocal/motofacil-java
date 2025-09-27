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

    // Criar nova moto vinculada a um pátio
    @PostMapping
    public Moto createMoto(@RequestBody Moto moto) {
        if (moto.getPatio() == null || moto.getPatio().getId() == null)
            throw new RuntimeException("Moto precisa ser vinculada a um pátio válido.");

        Patio patio = patioRepository.findById(moto.getPatio().getId())
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado."));
        moto.setPatio(patio);
        moto.setStatus("pendente"); // status inicial

        return motoRepository.save(moto);
    }

    // Atualizar localização de uma moto (recebe dados reais do ESP)
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

        // Chama simulador Python para pegar a posição sorteada
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + patio.getEsp32Central() + ":5001/simulate";
        Map<String, Object> payload = Map.of("id", moto.getId(), "x", dto.getX(), "y", dto.getY());
        ResponseEntity<Map> response = restTemplate.postForEntity(url, payload, Map.class);

        Map pythonResp = response.getBody();
        Map<String, Object> localizacaoSorteada = (Map<String, Object>) pythonResp.get("localizacaoSorteada");
        float x = Float.parseFloat(localizacaoSorteada.get("x").toString());
        float y = Float.parseFloat(localizacaoSorteada.get("y").toString());

        Location location = new Location();
        location.setX(x); // usa a posição sorteada do Python!
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

    // Atualizar status da moto (ex: para "mecanica", removendo localização/pátio)
    @PutMapping("/{id}/status")
    public ResponseEntity<Moto> updateMotoStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        String novoStatus = body.get("status");
        moto.setStatus(novoStatus);

        if ("mecanica".equalsIgnoreCase(novoStatus)) {
            moto.setPatio(null);
            // Se quiser remover localização, pode buscar por Location e deletar, ou só não
            // exibir no front
        } else if ("patio".equalsIgnoreCase(novoStatus)) {
            String patioIdStr = body.get("patioId");
            Long patioId = patioIdStr != null ? Long.valueOf(patioIdStr) : null;
            if (patioId == null)
                throw new RuntimeException("Para voltar ao pátio, informe o patioId");
            Patio patio = patioRepository.findById(patioId)
                    .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
            moto.setPatio(patio);
            // status já está setado pra "patio"
            // Não cria localização aqui: o front deve chamar /location depois, para setar a
            // nova localização
        }

        motoRepository.save(moto);
        return ResponseEntity.ok(moto);
    }

    // Última localização de uma moto
    @GetMapping("/{id}/location")
    public Location getMotoLocation(@PathVariable Long id) {
        return locationRepository.findTopByMotoIdOrderByTimestampDesc(id).orElse(null);
    }

    // Histórico completo de uma moto
    @GetMapping("/{id}/history")
    public List<Location> getMotoHistory(@PathVariable Long id) {
        return locationRepository.findByMotoIdOrderByTimestampDesc(id);
    }

    // Listar todas as motos
    @GetMapping
    public List<Moto> getAllMotos() {
        return motoRepository.findAll();
    }

    // Buscar moto por ID
    @GetMapping("/{id}")
    public Moto getMotoById(@PathVariable Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }

    // Listar motos por pátio
    @GetMapping("/patio/{patioId}")
    public List<Moto> getMotosByPatio(@PathVariable Long patioId) {
        Patio patio = patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
        return motoRepository.findByPatio(patio);
    }
}