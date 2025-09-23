package com.motofacil.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float x;
    private float y;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "moto_id") // FK para Moto
    private Moto moto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Moto getMoto() { return moto; }
    public void setMoto(Moto moto) { this.moto = moto; }
}
