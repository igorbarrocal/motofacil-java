package com.motofacil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // desabilita CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // permite todas as requisições
            );

        // JWT ou outros filtros podem ser adicionados aqui futuramente

        return http.build();
    }
}
