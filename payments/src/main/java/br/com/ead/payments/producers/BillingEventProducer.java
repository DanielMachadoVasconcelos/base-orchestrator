package br.com.ead.payments.producers;

import br.com.ead.commons.BaseEvent;
import br.com.ead.infrastructure.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BillingEventProducer implements EventProducer {

    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void producer(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}