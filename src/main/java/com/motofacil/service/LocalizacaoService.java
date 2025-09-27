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

        // Exemplo: Simple trilateration com 4 cantos e 1 central
        double somaX = 0, somaY = 0, somaRSSI = 0;
        for (int i = 0; i < coords.size(); i++) {
            double rssi = dados.getRssiPorEsp().getOrDefault("esp" + (i+1), -80.0);
            somaX += coords.get(i).getX() * Math.abs(rssi);
            somaY += coords.get(i).getY() * Math.abs(rssi);
            somaRSSI += Math.abs(rssi);
        }
        double x = somaX / somaRSSI;
        double y = somaY / somaRSSI;

        LocalizacaoHistorico loc = new LocalizacaoHistorico();
        loc.setMoto(moto);
        loc.setX(x);
        loc.setY(y);
        loc.setRssiCentral(dados.getRssiPorEsp().getOrDefault("esp_central", -80.0));
        loc.setTimestamp(LocalDateTime.now());
        historicoRepository.save(loc);

        return loc;
    }

    public List<LocalizacaoHistorico> historicoPorMoto(Long motoId) {
        return historicoRepository.findByMotoIdOrderByTimestampDesc(motoId);
    }
}