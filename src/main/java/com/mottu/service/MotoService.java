package com.mottu.service;

import com.mottu.model.Moto;
import com.mottu.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {
    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> listar(){ return motoRepository.findAll(); }
    public Moto obter(Long id){ return motoRepository.findById(id).orElse(null); }
    public Moto criar(Moto m){ return motoRepository.save(m); }
    public Moto atualizar(Long id, Moto m){
        Moto atual = obter(id);
        if(atual==null) return null;
        atual.setPlaca(m.getPlaca());
        atual.setChassi(m.getChassi());
        atual.setStatus(m.getStatus());
        atual.setLocalizacao(m.getLocalizacao());
        atual.setCnpj(m.getCnpj());
        atual.setCodigoAdm(m.getCodigoAdm());
        return motoRepository.save(atual);
    }
    public void deletar(Long id){ motoRepository.deleteById(id); }

    public Moto findMoto(String placa, String chassi){
        if(placa!=null && !placa.isEmpty()) {
            Moto m = motoRepository.findByPlaca(placa);
            if(m!=null) return m;
        }
        if(chassi!=null && !chassi.isEmpty()) return motoRepository.findByChassi(chassi);
        return null;
    }

    public Moto moveToOficina(Long id){
        Moto m = obter(id);
        if(m==null) return null;
        m.setStatus("OFICINA");
        return motoRepository.save(m);
    }
    public Moto returnToPatio(Long id){
        Moto m = obter(id);
        if(m==null) return null;
        m.setStatus("PATIO");
        return motoRepository.save(m);
    }
}
