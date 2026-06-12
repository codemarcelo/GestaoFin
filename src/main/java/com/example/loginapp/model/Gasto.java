package com.example.loginapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Gasto {
    private Long id;
    private Long userId;
    private String titulo;
    private BigDecimal valor;
    private BigDecimal valorComJuros; // Valor total com juros
    private BigDecimal valorParcela;   // Valor de cada parcela
    private BigDecimal jurosTotal; // Juros totais
    private Integer totalParcelas;
    private Integer parcelasPagas;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;
    private String status; // ATIVO, PAGO, CANCELADO
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoInicio;

    public Gasto() {}

    public Gasto(Long id, Long userId, String titulo, BigDecimal valor, BigDecimal valorComJuros, BigDecimal valorParcela,
                 BigDecimal jurosTotal, Integer totalParcelas, Integer parcelasPagas, LocalDate dataCriacao, String status,
                 LocalDate dataVencimentoInicio) {
        this.id = id;
        this.userId = userId;
        this.titulo = titulo;
        this.valor = valor;
        this.valorComJuros = valorComJuros;
        this.valorParcela = valorParcela;
        this.jurosTotal = jurosTotal;
        this.totalParcelas = totalParcelas;
        this.parcelasPagas = parcelasPagas;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.dataVencimentoInicio = dataVencimentoInicio;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public BigDecimal getValorComJuros() {
        return valorComJuros;
    }

    public void setValorComJuros(BigDecimal valorComJuros) {
        this.valorComJuros = valorComJuros;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public BigDecimal getJurosTotal() {
        return jurosTotal;
    }

    public void setJurosTotal(BigDecimal jurosTotal) {
        this.jurosTotal = jurosTotal;
    }

    public Integer getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(Integer totalParcelas) {
        this.totalParcelas = totalParcelas;
    }

    public Integer getParcelasPagas() {
        return parcelasPagas;
    }

    public void setParcelasPagas(Integer parcelasPagas) {
        this.parcelasPagas = parcelasPagas;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataVencimentoInicio() {
        return dataVencimentoInicio;
    }

    public void setDataVencimentoInicio(LocalDate dataVencimentoInicio) {
        this.dataVencimentoInicio = dataVencimentoInicio;
    }

    // Métodos de negócio
    public LocalDate getDataVencimentoFim() {
        if (dataVencimentoInicio == null || totalParcelas == null || totalParcelas <= 1) {
            return dataVencimentoInicio;
        }
        return dataVencimentoInicio.plusMonths(totalParcelas - 1);
    }

    public String getStatusPagamento() {
        if (parcelasPagas != null && totalParcelas != null && parcelasPagas.equals(totalParcelas)) {
            return "Quitado";
        }

        if (dataVencimentoInicio == null) {
            return "Em dia";
        }

        LocalDate hoje = LocalDate.now();
        int expectedPaid = 0;
        for (int i = 0; i < totalParcelas; i++) {
            LocalDate vencimentoParcela = dataVencimentoInicio.plusMonths(i);
            if (!vencimentoParcela.isAfter(hoje)) {
                expectedPaid++;
            } else {
                break;
            }
        }

        if (parcelasPagas < expectedPaid) {
            return "Em atraso";
        }
        return "Em dia";
    }

    public Integer getParcelasRestantes() {
        return totalParcelas - parcelasPagas;
    }

    public BigDecimal getJurosPorParcela() {
        if (totalParcelas == 0) return BigDecimal.ZERO;
        return jurosTotal.divide(new BigDecimal(totalParcelas), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getValorRestante() {
        return getValorParcela().multiply(new BigDecimal(getParcelasRestantes()));
    }

    public BigDecimal getValorPago() {
        return getValorParcela().multiply(new BigDecimal(parcelasPagas));
    }

    public Double getPercentualPago() {
        if (totalParcelas == 0) return 0.0;
        return (double) (parcelasPagas * 100) / totalParcelas;
    }
}

