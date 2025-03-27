package br.com.creditos.api.service;

import br.com.creditos.api.dto.CreditoDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditoKafkaPublisher {

    private static final String TOPIC_CREDITO = "consulta-creditos";

    private final KafkaTemplate<String, List<CreditoDTO>> kafkaTemplate;

    public CreditoKafkaPublisher(KafkaTemplate<String, List<CreditoDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(List<CreditoDTO> creditosDTO) {
        kafkaTemplate.send(TOPIC_CREDITO, creditosDTO);
    }
}
