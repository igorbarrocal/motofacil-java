package com.motofacil.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LocalizacaoHistorico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double x;
    private double y;
    private double rssiCentral;
    private LocalDateTime timestamp;
    @JoinColumn(name = "moto_id")
    @ManyToOne
    private Moto moto;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRssiCentral() {
        return rssiCentral;
    }

    public void setRssiCentral(double rssiCentral) {
        this.rssiCentral = rssiCentral;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }
}