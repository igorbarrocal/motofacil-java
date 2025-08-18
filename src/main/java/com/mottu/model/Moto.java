package com.mottu.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Moto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String placa;

    @Column(unique = true)
    private String chassi;

    private String status; // PATIO, OFICINA, RUA, etc.
    private String localizacao;
    private String cnpj;
    private String codigoAdm;

    // Getters/Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public String getPlaca(){return placa;}
    public void setPlaca(String placa){this.placa=placa;}
    public String getChassi(){return chassi;}
    public void setChassi(String chassi){this.chassi=chassi;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
    public String getLocalizacao(){return localizacao;}
    public void setLocalizacao(String localizacao){this.localizacao=localizacao;}
    public String getCnpj(){return cnpj;}
    public void setCnpj(String cnpj){this.cnpj=cnpj;}
    public String getCodigoAdm(){return codigoAdm;}
    public void setCodigoAdm(String codigoAdm){this.codigoAdm=codigoAdm;}
}
