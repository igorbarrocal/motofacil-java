package com.motofacil.service;

import com.motofacil.dto.LoginRequestDTO;
import com.motofacil.dto.LoginResponseDTO;
import com.motofacil.entity.Administrador;
import com.motofacil.entity.Funcionario;
import com.motofacil.repository.AdministradorRepository;
import com.motofacil.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdministradorRepository adminRepo;

    @Autowired
    private FuncionarioRepository funcRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login - retorna informações reais do usuário
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Administrador> adminOpt = adminRepo.findByEmail(loginRequest.getEmail());
        if (adminOpt.isPresent() && passwordEncoder.matches(loginRequest.getSenha(), adminOpt.get().getSenha())) {
            Administrador admin = adminOpt.get();
            return new LoginResponseDTO(admin.getNome(), admin.getEmail(), "Administrador");
        }

        Optional<Funcionario> funcOpt = funcRepo.findByEmail(loginRequest.getEmail());
        if (funcOpt.isPresent() && passwordEncoder.matches(loginRequest.getSenha(), funcOpt.get().getSenha())) {
            Funcionario func = funcOpt.get();
            return new LoginResponseDTO(func.getNome(), func.getEmail(), "Funcionario");
        }

        return null; // usuário ou senha incorretos
    }

    // Cadastro Admin
    public void registerAdmin(Administrador admin) throws Exception {
        if (adminRepo.existsByEmail(admin.getEmail())) {
            throw new Exception("Email já cadastrado!");
        }
        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        adminRepo.save(admin);
    }

    // Cadastro Funcionario
    public void registerFuncionario(Funcionario func) throws Exception {
        if (funcRepo.existsByEmail(func.getEmail())) {
            throw new Exception("Email já cadastrado!");
        }
        func.setSenha(passwordEncoder.encode(func.getSenha()));
        funcRepo.save(func);
    }
}
