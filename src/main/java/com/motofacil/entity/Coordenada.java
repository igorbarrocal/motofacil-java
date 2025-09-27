package com.motofacil.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Coordenada {
    private double x;
    private double y;

    // Getters e Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
}