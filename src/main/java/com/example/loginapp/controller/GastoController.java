package com.example.loginapp.controller;

import com.example.loginapp.exception.BadRequestException;
import com.example.loginapp.exception.ResourceNotFoundException;
import com.example.loginapp.model.User;
import com.example.loginapp.model.dto.CreateGastoRequest;
import com.example.loginapp.model.dto.GastoResponse;
import com.example.loginapp.model.dto.GastoSummaryResponse;
import com.example.loginapp.repository.UserRepository;
import com.example.loginapp.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gastos")
@CrossOrigin(origins = "*")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UserRepository userRepository;

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(user -> user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @GetMapping
    public ResponseEntity<List<GastoResponse>> listarGastos(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<GastoResponse> gastos = gastoService.getAllGastos(userId);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/resumo")
    public ResponseEntity<GastoSummaryResponse> obterResumo(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        GastoSummaryResponse resumo = gastoService.obterResumo(userId);
        return ResponseEntity.ok(resumo);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> criarGasto(
            Authentication authentication,
            @RequestBody CreateGastoRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);

        if (request.getTitulo() == null || request.getTitulo().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Título do gasto é obrigatório");
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getValor() == null || request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Valor do gasto deve ser maior que zero");
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getValorParcela() == null || request.getValorParcela().compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Valor da parcela deve ser maior que zero");
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getTotalParcelas() == null || request.getTotalParcelas() <= 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Quantidade de parcelas deve ser maior que zero");
            return ResponseEntity.badRequest().body(response);
        }

        GastoResponse gasto = gastoService.criarGasto(userId, request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Gasto criado com sucesso");
        response.put("gasto", gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/parcelas")
    public ResponseEntity<Map<String, Object>> atualizarParcelasPagas(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {
        Integer parcelasPagas = body.get("parcelasPagas");

        try {
            GastoResponse gasto = gastoService.atualizarParcelasPagas(id, parcelasPagas);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Parcelas atualizadas com sucesso");
            response.put("gasto", gasto);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (BadRequestException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/{id}/desmarcar-parcela")
    public ResponseEntity<Map<String, Object>> desmarcarParcela(@PathVariable Long id) {
        try {
            GastoResponse gasto = gastoService.desmarcarParcela(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Parcela desmarcada com sucesso");
            response.put("gasto", gasto);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarGasto(@PathVariable Long id) {
        gastoService.deletarGasto(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Gasto deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}

