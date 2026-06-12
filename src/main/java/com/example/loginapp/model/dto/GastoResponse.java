package com.example.loginapp.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GastoResponse {
    private Long id;
    private String titulo;
    private BigDecimal valor;
    private BigDecimal valorComJuros;
    private BigDecimal jurosTotal;
    private BigDecimal valorParcela;
    private BigDecimal jurosPorParcela;
    private Integer totalParcelas;
    private Integer parcelasPagas;
    private Integer parcelasRestantes;
    private BigDecimal valorPago;
    private BigDecimal valorRestante;
    private BigDecimal percentualJuros;
    private Double percentualPago;
    private LocalDate dataCriacao;
    private String status;

    public GastoResponse() {}

    public GastoResponse(Long id, String titulo, BigDecimal valor, BigDecimal valorComJuros, BigDecimal jurosTotal,
                        BigDecimal valorParcela, BigDecimal jurosPorParcela, Integer totalParcelas, 
                        Integer parcelasPagas, Integer parcelasRestantes, BigDecimal valorPago, 
                        BigDecimal valorRestante, BigDecimal percentualJuros, Double percentualPago, 
                        LocalDate dataCriacao, String status) {
        this.id = id;
        this.titulo = titulo;
        this.valor = valor;
        this.valorComJuros = valorComJuros;
        this.jurosTotal = jurosTotal;
        this.valorParcela = valorParcela;
        this.jurosPorParcela = jurosPorParcela;
        this.totalParcelas = totalParcelas;
        this.parcelasPagas = parcelasPagas;
        this.parcelasRestantes = parcelasRestantes;
        this.valorPago = valorPago;
        this.valorRestante = valorRestante;
        this.percentualJuros = percentualJuros;
        this.percentualPago = percentualPago;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    
    public BigDecimal getValorComJuros() { return valorComJuros; }
    public void setValorComJuros(BigDecimal valorComJuros) { this.valorComJuros = valorComJuros; }
    
    public BigDecimal getJurosTotal() { return jurosTotal; }
    public void setJurosTotal(BigDecimal jurosTotal) { this.jurosTotal = jurosTotal; }
    
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    
    public BigDecimal getJurosPorParcela() { return jurosPorParcela; }
    public void setJurosPorParcela(BigDecimal jurosPorParcela) { this.jurosPorParcela = jurosPorParcela; }
    
    public Integer getTotalParcelas() { return totalParcelas; }
    public void setTotalParcelas(Integer totalParcelas) { this.totalParcelas = totalParcelas; }
    
    public Integer getParcelasPagas() { return parcelasPagas; }
    public void setParcelasPagas(Integer parcelasPagas) { this.parcelasPagas = parcelasPagas; }
    
    public Integer getParcelasRestantes() { return parcelasRestantes; }
    public void setParcelasRestantes(Integer parcelasRestantes) { this.parcelasRestantes = parcelasRestantes; }
    
    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }
    
    public BigDecimal getValorRestante() { return valorRestante; }
    public void setValorRestante(BigDecimal valorRestante) { this.valorRestante = valorRestante; }
    
    public BigDecimal getPercentualJuros() { return percentualJuros; }
    public void setPercentualJuros(BigDecimal percentualJuros) { this.percentualJuros = percentualJuros; }
    
    public Double getPercentualPago() { return percentualPago; }
    public void setPercentualPago(Double percentualPago) { this.percentualPago = percentualPago; }
    
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

