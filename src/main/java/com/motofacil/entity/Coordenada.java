package com.motofacil.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class Coordenada {
    private double x;
    private double y;

    @Column(name = "coordenadas_extremidade_order") // mapeia o campo para Oracle
    private int order;

    // Getters e Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
}