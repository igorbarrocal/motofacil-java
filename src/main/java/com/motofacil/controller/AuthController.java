package com.motofacil.controller;

import com.motofacil.dto.LoginRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequest) {

        return "jwt-token-exemplo";
    }
}
