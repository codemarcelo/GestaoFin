package com.example.loginapp.model.dto;

import java.math.BigDecimal;

public class CreateGastoRequest {
    private String titulo;
    private BigDecimal valor;
    private BigDecimal valorParcela;
    private Integer totalParcelas;
    private String dataVencimentoInicio; // Recebido como String (yyyy-MM-dd)
    private Integer parcelasPagas; // Recebido opcionalmente para gastos em andamento

    public CreateGastoRequest() {}

    public CreateGastoRequest(String titulo, BigDecimal valor, BigDecimal valorParcela, Integer totalParcelas, String dataVencimentoInicio, Integer parcelasPagas) {
        this.titulo = titulo;
        this.valor = valor;
        this.valorParcela = valorParcela;
        this.totalParcelas = totalParcelas;
        this.dataVencimentoInicio = dataVencimentoInicio;
        this.parcelasPagas = parcelasPagas;
    }

    public String getDataVencimentoInicio() {
        return dataVencimentoInicio;
    }

    public void setDataVencimentoInicio(String dataVencimentoInicio) {
        this.dataVencimentoInicio = dataVencimentoInicio;
    }

    public Integer getParcelasPagas() {
        return parcelasPagas;
    }

    public void setParcelasPagas(Integer parcelasPagas) {
        this.parcelasPagas = parcelasPagas;
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

