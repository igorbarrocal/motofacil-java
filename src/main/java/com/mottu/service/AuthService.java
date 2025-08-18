package com.mottu.service;

import com.mottu.model.Usuario;
import com.mottu.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario registerAdmin(Usuario u){
        u.setSenha(encoder.encode(u.getSenha()));
        u.setRole("ADMIN");
        return usuarioRepository.save(u);
    }

    public Usuario registerFuncionario(Usuario u){
        u.setSenha(encoder.encode(u.getSenha()));
        u.setRole("FUNCIONARIO");
        return usuarioRepository.save(u);
    }

    public boolean login(Usuario u){
        Usuario db = usuarioRepository.findByEmail(u.getEmail());
        if(db==null) return false;
        return encoder.matches(u.getSenha(), db.getSenha());
    }
}
