package br.com.creditos.api.service;

import br.com.creditos.api.dto.CreditoDTO;
import br.com.creditos.api.exception.CreditoNaoEncontradoException;
import br.com.creditos.api.model.Credito;
import br.com.creditos.api.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CreditoServiceTest {
    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private CreditoKafkaPublisher creditoKafkaPublisher;

    @InjectMocks
    private CreditoService creditoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarCreditosNumeroNfse() throws CreditoNaoEncontradoException {
        Credito credito = new Credito(
                1L,
                "123456",
                "7891011",
                LocalDate.of(2024, 2, 25),
                new BigDecimal("1500.75"),
                "ISSQN",
                true,
                new BigDecimal("5"),
                new BigDecimal("30000"),
                new BigDecimal("5000"),
                new BigDecimal("25000")
        );
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(Optional.of(credito));

        CreditoDTO result = creditoService.buscarCreditoNumeroCredito("123456");

        assertNotNull(result);
        assertEquals("123456", result.numeroCredito());
    }

    @Test
    void buscarCreditoNumeroCredito_NotFound() {
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(Optional.empty());

        assertThrows(CreditoNaoEncontradoException.class, () -> {
            creditoService.buscarCreditoNumeroCredito("123456");
        });
    }
}
