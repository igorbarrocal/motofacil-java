package com.mottu.service;

import com.mottu.model.Patio;
import com.mottu.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatioService {
    @Autowired
    private PatioRepository patioRepository;

    public List<Patio> listar(){return patioRepository.findAll();}
    public Patio obter(Long id){return patioRepository.findById(id).orElse(null);}
    public Patio criar(Patio p){return patioRepository.save(p);}
    public Patio atualizar(Long id, Patio p){
        Patio atual = obter(id);
        if(atual==null) return null;
        atual.setNome(p.getNome());
        atual.setAtivo(p.isAtivo());
        return patioRepository.save(atual);
    }
    public void deletar(Long id){patioRepository.deleteById(id);}

    public Patio getActivePatio(){
        return patioRepository.findFirstByAtivoTrue();
    }
}
