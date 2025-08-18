package com.mottu.service;

import com.mottu.model.Relatorio;
import com.mottu.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {
    @Autowired
    private RelatorioRepository relatorioRepository;

    public List<Relatorio> listar(){return relatorioRepository.findAll();}
    public Relatorio obter(Long id){return relatorioRepository.findById(id).orElse(null);}
    public Relatorio criar(Relatorio r){return relatorioRepository.save(r);}
    public void deletar(Long id){relatorioRepository.deleteById(id);}
}
