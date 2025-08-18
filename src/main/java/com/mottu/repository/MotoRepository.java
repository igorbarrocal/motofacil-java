package com.mottu.repository;

import com.mottu.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    Moto findByPlaca(String placa);
    Moto findByChassi(String chassi);
}
