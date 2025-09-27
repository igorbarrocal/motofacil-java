package com.motofacil.dto;

import java.util.Map;

public class ESPDadosDTO {
    private Long motoId;
    private Map<String, Double> rssiPorEsp; // chave: id do ESP, valor: RSSI

    // Getters e Setters
    public Long getMotoId() { return motoId; }
    public void setMotoId(Long motoId) { this.motoId = motoId; }
    public Map<String, Double> getRssiPorEsp() { return rssiPorEsp; }
    public void setRssiPorEsp(Map<String, Double> rssiPorEsp) { this.rssiPorEsp = rssiPorEsp; }
}