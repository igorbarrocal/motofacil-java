package com.motofacil.repository;

import com.motofacil.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmail(String email);
    boolean existsByCodigoAcessoPatio(String codigoAcessoPatio);
}
