package com.mottu.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Relatorio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private LocalDate data;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getDescricao(){return descricao;}
    public void setDescricao(String descricao){this.descricao=descricao;}
    public LocalDate getData(){return data;}
    public void setData(LocalDate data){this.data=data;}
}
