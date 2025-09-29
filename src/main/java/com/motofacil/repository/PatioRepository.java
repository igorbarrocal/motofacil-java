package com.motofacil.repository;

import com.motofacil.entity.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatioRepository extends JpaRepository<Patio, Long> {
    boolean existsByCodigoUnico(String codigoUnico);
}