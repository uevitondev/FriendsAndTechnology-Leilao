package com.friendsandtechnology.leilao.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Lance implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Double valorLance;
    private String dataLance;
    private String comprador;

    public Lance() {
    }

    public Lance(String id, Double valorLance, String dataLance, String comprador) {
        super();
        this.id = id;
        this.valorLance = valorLance;
        this.dataLance = dataLance;
        this.comprador = comprador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValorLance() {
        return valorLance;
    }

    public void setValorLance(Double valorLance) {
        this.valorLance = valorLance;
    }

    public String getDataLance() {
        return dataLance;
    }

    public void setDataLance(String dataLance) {
        this.dataLance = dataLance;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

}
