package com.motofacil.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Column(unique = true)
    private String codigoUnico;

    private String esp32Central;

    @ElementCollection
    private float[] coordenadasExtremidade = new float[4];

    @ManyToOne
    private Administrador administrador;
}
