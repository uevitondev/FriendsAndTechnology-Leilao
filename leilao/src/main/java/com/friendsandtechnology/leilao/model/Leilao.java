package com.friendsandtechnology.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.friendsandtechnology.leilao.enums.STATUS;

@Document
public class Leilao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Binary image;
    private String titulo;
    private String descricao;
    private String categoria;
    private Double valorInicial;
    private Double lanceMinimo;
    private String dataLimite;
    private String vendedor;
    private String dataCadastro;
    private STATUS status;

    @DBRef(lazy = false)
    private List<Lance> lances = new ArrayList<>();

    public Leilao() {

    }

    public Leilao(String id, Binary image, String titulo, String descricao, String categoria, Double valorInicial, Double lanceMinimo,
            String dataLimite, String vendedor, String dataCadastro, STATUS status) {
        super();
        this.id = id;
        this.setImage(image);
        this.titulo = titulo;
        this.descricao = descricao;
        this.setCategoria(categoria);
        this.valorInicial = valorInicial;
        this.lanceMinimo = lanceMinimo;
        this.dataLimite = dataLimite;
        this.vendedor = vendedor;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Double getLanceMinimo() {
        return lanceMinimo;
    }

    public void setLanceMinimo(Double lanceMinimo) {
        this.lanceMinimo = lanceMinimo;
    }

    public String getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(String dataLimite) {
        this.dataLimite = dataLimite;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public List<Lance> getLances() {
        return lances;
    }

    public void setLances(List<Lance> lances) {
        this.lances = lances;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

}