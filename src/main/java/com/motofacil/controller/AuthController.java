package com.motofacil.controller;

import com.motofacil.dto.LoginRequestDTO;
import com.motofacil.dto.LoginResponseDTO;
import com.motofacil.entity.Administrador;
import com.motofacil.entity.Funcionario;
import com.motofacil.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.login(loginRequest);
        if (response == null) {
            return ResponseEntity.status(401).body("Usuário ou senha incorretos");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Administrador admin) {
        try {
            authService.registerAdmin(admin);
            return ResponseEntity.ok("Administrador cadastrado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/funcionario")
    public ResponseEntity<?> registerFuncionario(@RequestBody Funcionario funcionario) {
        try {
            authService.registerFuncionario(funcionario);
            return ResponseEntity.ok("Funcionário cadastrado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
