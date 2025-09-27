package com.motofacil.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.motofacil.entity.LocalizacaoHistorico;
import com.motofacil.entity.Moto;

public interface LocalizacaoHistoricoRepository extends JpaRepository<LocalizacaoHistorico, Long> {
    Optional<LocalizacaoHistorico> findTopByMotoOrderByTimestampDesc(Moto moto);
    List<LocalizacaoHistorico> findByMotoOrderByTimestampDesc(Moto moto);
}