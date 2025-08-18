package com.mottu.repository;

import com.mottu.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {
    Patio findFirstByAtivoTrue();
}
