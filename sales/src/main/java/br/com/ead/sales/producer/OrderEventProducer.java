package br.com.ead.sales.producer;

import br.com.ead.commons.BaseEvent;
import br.com.ead.infrastructure.EventProducer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class OrderEventProducer implements EventProducer {

    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void producer(String topic, BaseEvent event) {
        log.info("Received a Event to be publish. Topic={}, Event={}", topic, event);
        this.kafkaTemplate.send(topic, event);
    }
}
