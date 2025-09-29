package com.motofacil.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String codigoUnico;
    private String esp32Central;

    // Coordenadas dos 5 ESPs (4 cantos + 1 central)
    @ElementCollection
    @CollectionTable(name = "PATIO_COORDENADAS_EXTREMIDADE", joinColumns = @JoinColumn(name = "PATIO_ID"))
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "X")),
            @AttributeOverride(name = "y", column = @Column(name = "Y")),
            @AttributeOverride(name = "order", column = @Column(name = "COORDENADAS_EXTREMIDADE_ORDER"))
    })
    private List<Coordenada> coordenadasExtremidade;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getEsp32Central() {
        return esp32Central;
    }

    public void setEsp32Central(String esp32Central) {
        this.esp32Central = esp32Central;
    }

    public List<Coordenada> getCoordenadasExtremidade() {
        return coordenadasExtremidade;
    }

    public void setCoordenadasExtremidade(List<Coordenada> coordenadasExtremidade) {
        this.coordenadasExtremidade = coordenadasExtremidade;
    }
}