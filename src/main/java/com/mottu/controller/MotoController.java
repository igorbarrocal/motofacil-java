package com.mottu.controller;

import com.mottu.model.Moto;
import com.mottu.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listar(){
        return ResponseEntity.ok(motoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> obter(@PathVariable Long id){
        Moto m = motoService.obter(id);
        if(m==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(m);
    }

    @PostMapping
    public ResponseEntity<Moto> criar(@RequestBody Moto m){
        return ResponseEntity.ok(motoService.criar(m));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody Moto m){
        Moto up = motoService.atualizar(id, m);
        if(up==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(up);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Moto> findMoto(@RequestParam(required = false) String placa,
                                         @RequestParam(required = false) String chassi){
        return ResponseEntity.ok(motoService.findMoto(placa, chassi));
    }

    @PostMapping("/{id}/move-to-oficina")
    public ResponseEntity<Moto> moveToOficina(@PathVariable Long id){
        Moto up = motoService.moveToOficina(id);
        if(up==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(up);
    }

    @PostMapping("/{id}/return-to-patio")
    public ResponseEntity<Moto> returnToPatio(@PathVariable Long id){
        Moto up = motoService.returnToPatio(id);
        if(up==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(up);
    }
}
