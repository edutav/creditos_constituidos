package br.com.creditos.api.service;

import br.com.creditos.api.dto.CreditoDTO;
import br.com.creditos.api.exception.CreditoNaoEncontradoException;
import br.com.creditos.api.model.Credito;
import br.com.creditos.api.repository.CreditoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditoService {
    private final CreditoRepository creditoRepository;
    private final CreditoKafkaPublisher kafkaPublisher;

    public CreditoService(CreditoRepository creditoRepository, CreditoKafkaPublisher kafkaPublisher) {
        this.creditoRepository = creditoRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    public List<CreditoDTO> listarCreditosNumeroNfse(String numeroNfse) {
        // Validação de entrada
        if (numeroNfse == null || numeroNfse.isEmpty()) {
            throw new IllegalArgumentException("Número da NFS-e não pode ser vazio.");
        }

        List<Credito> creditos = this.creditoRepository.findByNumeroNfse(numeroNfse);

        List<CreditoDTO> listCreditosDTO = creditos.stream().map(credito -> new CreditoDTO(
                credito.getId(),
                credito.getNumeroCredito(),
                credito.getNumeroNfse(),
                credito.getDataConstituicao(),
                credito.getValorIssqn(),
                credito.getTipoCredito(),
                credito.getSimplesNacional() ? "Sim" : "Não",
                credito.getAliquota(),
                (credito.getValorFaturado()),
                (credito.getValorDeducao()),
                (credito.getBaseCalculo())
        )).collect(Collectors.toList());

        this.kafkaPublisher.publish(listCreditosDTO);

        return listCreditosDTO;
    }

    public CreditoDTO buscarCreditoNumeroCredito(String numeroCredito) throws CreditoNaoEncontradoException {
        // Validação de entrada
        if (numeroCredito == null || numeroCredito.isEmpty()) {
            throw new IllegalArgumentException("Número do crédito não pode ser vazio.");
        }

        Optional<Credito> creditoOptional = creditoRepository.findByNumeroCredito(numeroCredito);

        // Regra de negócio: tratar crédito não encontrado
        Credito credito = creditoOptional.orElseThrow(() -> new CreditoNaoEncontradoException("Crédito não encontrado."));

        CreditoDTO dto = new CreditoDTO(
                credito.getId(),
                credito.getNumeroCredito(),
                credito.getNumeroNfse(),
                credito.getDataConstituicao(),
                credito.getValorIssqn(),
                credito.getTipoCredito(),
                credito.getSimplesNacional() ? "Sim" : "Não",
                credito.getAliquota(),
                (credito.getValorFaturado()),
                (credito.getValorDeducao()),
                (credito.getBaseCalculo())
        );

        List<CreditoDTO> list = new ArrayList<>();

        list.add(dto);

        this.kafkaPublisher.publish(list);

        return dto;
    }

}
