package br.com.creditos.api.controller;

import br.com.creditos.api.dto.CreditoDTO;
import br.com.creditos.api.exception.CreditoNaoEncontradoException;
import br.com.creditos.api.service.CreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "*")
public class CreditoController {
    private final CreditoService service;

    public CreditoController(CreditoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtém um crédito por número de crédito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crédito encontrado"),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado")
    })
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> buscarCreditoPorId(@PathVariable String numeroNfse) {
        try {
            List<CreditoDTO> list = service.listarCreditosNumeroNfse(numeroNfse);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtém um crédito por número de crédito 2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crédito encontrado"),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado")
    })
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> listarCreditosPorCliente(@PathVariable String numeroCredito) {
        CreditoDTO credito = null;
        try {
            credito = service.buscarCreditoNumeroCredito(numeroCredito);
        } catch (CreditoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(credito);
    }
}
