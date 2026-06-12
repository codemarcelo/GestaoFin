package com.example.loginapp.repository;

import com.example.loginapp.model.Gasto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GastoRepository {
    private static final Map<Long, Gasto> gastos = new HashMap<>();
    private static Long nextId = 1L;

    static {
        gastos.put(1L, new Gasto(1L, 1L, "Financiamento Carro",
            new BigDecimal("25000.00"), new BigDecimal("33500.00"), new BigDecimal("558.33"),
            new BigDecimal("8500.00"), 60, 12, LocalDate.now().minusMonths(1), "ATIVO",
            LocalDate.now().minusMonths(12)));

        gastos.put(2L, new Gasto(2L, 1L, "Empréstimo Pessoal",
            new BigDecimal("5000.00"), new BigDecimal("5750.00"), new BigDecimal("239.58"),
            new BigDecimal("750.00"), 24, 5, LocalDate.now().minusMonths(2), "ATIVO",
            LocalDate.now().minusMonths(5)));

        gastos.put(3L, new Gasto(3L, 1L, "Cartão de Crédito",
            new BigDecimal("3500.00"), new BigDecimal("3500.00"), new BigDecimal("3500.00"),
            BigDecimal.ZERO, 1, 0, LocalDate.now(), "ATIVO",
            LocalDate.now()));

        nextId = 4L;
    }

    public List<Gasto> findByUserId(Long userId) {
        return gastos.values().stream()
                .filter(g -> g.getUserId().equals(userId) && "ATIVO".equals(g.getStatus()))
                .collect(Collectors.toList());
    }

    public Optional<Gasto> findById(Long id) {
        return Optional.ofNullable(gastos.get(id));
    }

    public Gasto save(Gasto gasto) {
        if (gasto.getId() == null) {
            gasto.setId(nextId++);
            gasto.setDataCriacao(LocalDate.now());
            gasto.setStatus("ATIVO");
            gasto.setParcelasPagas(0);
        }
        gastos.put(gasto.getId(), gasto);
        return gasto;
    }

    public void delete(Long id) {
        Optional<Gasto> gasto = findById(id);
        if (gasto.isPresent()) {
            gasto.get().setStatus("CANCELADO");
        }
    }

    public void update(Long id, Gasto gastoAtualizado) {
        if (gastos.containsKey(id)) {
            Gasto existing = gastos.get(id);
            existing.setTitulo(gastoAtualizado.getTitulo());
            existing.setValor(gastoAtualizado.getValor());
            existing.setTotalParcelas(gastoAtualizado.getTotalParcelas());
            existing.setParcelasPagas(gastoAtualizado.getParcelasPagas());
        }
    }
}

