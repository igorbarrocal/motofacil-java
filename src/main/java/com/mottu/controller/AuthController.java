package com.mottu.controller;

import com.mottu.model.Usuario;
import com.mottu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario){
        boolean ok = authService.login(usuario);
        if(!ok) return ResponseEntity.status(401).body("Credenciais inválidas");
        return ResponseEntity.ok("Login OK");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Usuario> registerAdmin(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.registerAdmin(usuario));
    }

    @PostMapping("/register/funcionario")
    public ResponseEntity<Usuario> registerFuncionario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.registerFuncionario(usuario));
    }
}
