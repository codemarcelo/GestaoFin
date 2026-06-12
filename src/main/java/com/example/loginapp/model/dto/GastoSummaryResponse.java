package com.example.loginapp.model.dto;

import java.math.BigDecimal;

public class GastoSummaryResponse {
    private Integer totalGastos;
    private BigDecimal totalDividido;
    private BigDecimal totalPago;
    private BigDecimal totalRestante;

    public GastoSummaryResponse() {}

    public GastoSummaryResponse(Integer totalGastos, BigDecimal totalDividido, BigDecimal totalPago, BigDecimal totalRestante) {
        this.totalGastos = totalGastos;
        this.totalDividido = totalDividido;
        this.totalPago = totalPago;
        this.totalRestante = totalRestante;
    }

    public Integer getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(Integer totalGastos) {
        this.totalGastos = totalGastos;
    }

    public BigDecimal getTotalDividido() {
        return totalDividido;
    }

    public void setTotalDividido(BigDecimal totalDividido) {
        this.totalDividido = totalDividido;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalRestante() {
        return totalRestante;
    }

    public void setTotalRestante(BigDecimal totalRestante) {
        this.totalRestante = totalRestante;
    }
}

