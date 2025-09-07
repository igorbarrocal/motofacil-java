package com.motofacil.repository;

import com.motofacil.entity.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Optional<Moto> findByPlaca(String placa);
    Optional<Moto> findByChassi(String chassi);
    Optional<Moto> findByCodigo(String codigo);
}
