package com.motofacil.repository;

import com.motofacil.entity.LocalizacaoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocalizacaoHistoricoRepository extends JpaRepository<LocalizacaoHistorico, Long> {
    List<LocalizacaoHistorico> findByMotoIdOrderByTimestampDesc(Long motoId);
}