package com.mottu.controller;

import com.mottu.model.Relatorio;
import com.mottu.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<List<Relatorio>> listar(){
        return ResponseEntity.ok(relatorioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> obter(@PathVariable Long id){
        Relatorio r = relatorioService.obter(id);
        if(r==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<Relatorio> criar(@RequestBody Relatorio r){
        return ResponseEntity.ok(relatorioService.criar(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        relatorioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
