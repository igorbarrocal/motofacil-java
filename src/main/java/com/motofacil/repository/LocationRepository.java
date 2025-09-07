package com.motofacil.repository;

import com.motofacil.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findTopByOrderByTimestampDesc();
}
