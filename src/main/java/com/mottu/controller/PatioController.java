package com.mottu.controller;

import com.mottu.model.Patio;
import com.mottu.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patios")
public class PatioController {

    @Autowired
    private PatioService patioService;

    @GetMapping
    public ResponseEntity<List<Patio>> listar(){
        return ResponseEntity.ok(patioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patio> obter(@PathVariable Long id){
        Patio p = patioService.obter(id);
        if(p==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Patio> criar(@RequestBody Patio p){
        return ResponseEntity.ok(patioService.criar(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patio> atualizar(@PathVariable Long id, @RequestBody Patio p){
        Patio up = patioService.atualizar(id, p);
        if(up==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(up);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        patioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<Patio> getActivePatio(){
        return ResponseEntity.ok(patioService.getActivePatio());
    }
}
