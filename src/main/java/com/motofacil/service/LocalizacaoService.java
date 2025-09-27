package com.motofacil.service;

import com.motofacil.dto.ESPDadosDTO;
import com.motofacil.entity.*;
import com.motofacil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LocalizacaoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private LocalizacaoHistoricoRepository historicoRepository;

   
    public LocalizacaoHistorico triangulaEDefineLocalizacao(ESPDadosDTO dados) {
        Moto moto = motoRepository.findById(dados.getMotoId()).orElseThrow();
        Patio patio = moto.getPatio();
        List<Coordenada> coords = patio.getCoordenadasExtremidade();

        if (coords == null || coords.size() < 3) {
            throw new IllegalArgumentException("Pátio precisa ter pelo menos 3 coordenadas extremidade para triangulação.");
        }

        double somaX = 0, somaY = 0, somaRSSI = 0;
        for (int i = 0; i < coords.size(); i++) {
            String espNome = "esp" + (i + 1);
            double rssi = dados.getRssiPorEsp().getOrDefault(espNome, -80.0);
            somaX += coords.get(i).getX() * Math.abs(rssi);
            somaY += coords.get(i).getY() * Math.abs(rssi);
            somaRSSI += Math.abs(rssi);
        }
        double x = somaRSSI != 0 ? somaX / somaRSSI : coords.get(0).getX();
        double y = somaRSSI != 0 ? somaY / somaRSSI : coords.get(0).getY();

        x = Math.max(0, Math.min(x, 1));
        y = Math.max(0, Math.min(y, 1));

        LocalizacaoHistorico loc = new LocalizacaoHistorico();
        loc.setMoto(moto);
        loc.setX((float)x); 
        loc.setY((float)y);
        loc.setRssiCentral(dados.getRssiPorEsp().getOrDefault("esp_central", -80.0));
        loc.setTimestamp(LocalDateTime.now());
        historicoRepository.save(loc);

        return loc;
    }

    
   public List<LocalizacaoHistorico> historicoPorMoto(Long motoId) {
        Moto moto = motoRepository.findById(motoId).orElseThrow();
        return historicoRepository.findByMotoOrderByTimestampDesc(moto);
    }

    /**
     * Retorna a última localização da moto, exatamente como o frontend espera.
     */
    public LocalizacaoHistorico ultimaLocalizacaoPorMoto(Long motoId) {
        Moto moto = motoRepository.findById(motoId).orElseThrow();
        return historicoRepository.findTopByMotoOrderByTimestampDesc(moto).orElse(null);
    }
}