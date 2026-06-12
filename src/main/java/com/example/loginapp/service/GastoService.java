package com.example.loginapp.service;

import com.example.loginapp.exception.BadRequestException;
import com.example.loginapp.exception.ResourceNotFoundException;
import com.example.loginapp.model.Gasto;
import com.example.loginapp.model.dto.CreateGastoRequest;
import com.example.loginapp.model.dto.GastoResponse;
import com.example.loginapp.model.dto.GastoSummaryResponse;
import com.example.loginapp.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    public List<GastoResponse> getAllGastos(Long userId) {
        List<Gasto> gastos = gastoRepository.findByUserId(userId);
        return gastos.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public GastoResponse criarGasto(Long userId, CreateGastoRequest request) {
        BigDecimal valorParcela = request.getValorParcela();
        Integer totalParcelas = request.getTotalParcelas();
        BigDecimal valorGasto = request.getValor();

        if (valorParcela == null || totalParcelas == null || totalParcelas <= 0) {
            throw new BadRequestException("Valor da parcela e quantidade de parcelas são obrigatórios");
        }

        if (valorGasto == null || valorGasto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Valor do gasto deve ser maior que zero");
        }

        BigDecimal valorComJuros = valorParcela.multiply(new BigDecimal(totalParcelas));
        BigDecimal jurosTotal = valorComJuros.subtract(valorGasto);

        Gasto gasto = new Gasto();
        gasto.setUserId(userId);
        gasto.setTitulo(request.getTitulo());
        gasto.setValor(valorGasto);
        gasto.setValorParcela(valorParcela);
        gasto.setJurosTotal(jurosTotal);
        gasto.setValorComJuros(valorComJuros);
        gasto.setTotalParcelas(totalParcelas);
        gasto.setParcelasPagas(0);

        Gasto salvo = gastoRepository.save(gasto);
        return convertToResponse(salvo);
    }

    public GastoResponse atualizarParcelasPagas(Long gastoId, Integer parcelasPagas) {
        Gasto gasto = gastoRepository.findById(gastoId)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado"));

        if (parcelasPagas > gasto.getTotalParcelas()) {
            throw new BadRequestException("Número de parcelas pagas não pode ultrapassar o total de parcelas!");
        }

        gasto.setParcelasPagas(parcelasPagas);
        gastoRepository.update(gastoId, gasto);

        return convertToResponse(gasto);
    }

    public GastoResponse desmarcarParcela(Long gastoId) {
        Gasto gasto = gastoRepository.findById(gastoId)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado"));

        if (gasto.getParcelasPagas() > 0) {
            gasto.setParcelasPagas(gasto.getParcelasPagas() - 1);
            gastoRepository.update(gastoId, gasto);
        }

        return convertToResponse(gasto);
    }

    public void deletarGasto(Long gastoId) {
        gastoRepository.delete(gastoId);
    }

    public GastoSummaryResponse obterResumo(Long userId) {
        List<Gasto> gastos = gastoRepository.findByUserId(userId);

        BigDecimal totalDividido = gastos.stream()
                .map(Gasto::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPago = gastos.stream()
                .map(Gasto::getValorPago)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRestante = totalDividido.subtract(totalPago);

        return new GastoSummaryResponse(
                gastos.size(),
                totalDividido,
                totalPago,
                totalRestante
        );
    }

    private GastoResponse convertToResponse(Gasto gasto) {
        BigDecimal percentualJuros = BigDecimal.ZERO;
        if (gasto.getJurosTotal().compareTo(BigDecimal.ZERO) > 0 && gasto.getValor().compareTo(BigDecimal.ZERO) > 0) {
            percentualJuros = gasto.getJurosTotal()
                    .divide(gasto.getValor(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100))
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            percentualJuros = null;
        }

        return new GastoResponse(
                gasto.getId(),
                gasto.getTitulo(),
                gasto.getValor(),
                gasto.getValorComJuros(),
                gasto.getJurosTotal(),
                gasto.getValorParcela(),
                gasto.getJurosPorParcela(),
                gasto.getTotalParcelas(),
                gasto.getParcelasPagas(),
                gasto.getParcelasRestantes(),
                gasto.getValorPago(),
                gasto.getValorRestante(),
                percentualJuros,
                gasto.getPercentualPago(),
                gasto.getDataCriacao(),
                gasto.getStatus()
        );
    }
}

