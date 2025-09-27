package com.motofacil.controller;

import com.motofacil.dto.ESPDadosDTO;
import com.motofacil.entity.LocalizacaoHistorico;
import com.motofacil.service.LocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localizacao")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService service;

    @PostMapping("/triangulacao")
    public LocalizacaoHistorico triangulacao(@RequestBody ESPDadosDTO dados) {
        return service.triangulaEDefineLocalizacao(dados);
    }

    @GetMapping("/historico/{motoId}")
    public List<LocalizacaoHistorico> historico(@PathVariable Long motoId) {
        return service.historicoPorMoto(motoId);
    }
}