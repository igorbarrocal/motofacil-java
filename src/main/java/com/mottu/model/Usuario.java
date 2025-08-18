package com.mottu.model;

import javax.persistence.*;

@Entity
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String senha;
    private String nome;
    private String role=  "USER"; // ADMIN, FUNCIONARIO

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha=senha;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome=nome;}
    public String getRole(){return role;}
    public void setRole(String role){this.role=role;}
}
