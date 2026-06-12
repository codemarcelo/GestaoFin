package com.example.loginapp.model.dto;

import java.math.BigDecimal;

public class CreateGastoRequest {
    private String titulo;
    private BigDecimal valor;
    private BigDecimal valorParcela;
    private Integer totalParcelas;

    public CreateGastoRequest() {}

    public CreateGastoRequest(String titulo, BigDecimal valor, BigDecimal valorParcela, Integer totalParcelas) {
        this.titulo = titulo;
        this.valor = valor;
        this.valorParcela = valorParcela;
        this.totalParcelas = totalParcelas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Integer getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(Integer totalParcelas) {
        this.totalParcelas = totalParcelas;
    }
}

