package br.com.ead.payments.consumer;

import br.com.ead.payments.BillOrderCommand;
import br.com.ead.payments.OrderBilledEvent;
import br.com.ead.payments.producers.BillingEventProducer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class OrdersBillingConsumer {

    private final BillingEventProducer producer;

    @KafkaListener(topics = "BillOrderCommand", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload BillOrderCommand event) {
        log.info("Received an Bill Order Command. {}", event);
        log.warn("TODO: should process the billing command.");
        log.warn("TODO: should save the billing data in the database.");

        log.info("Emitting an OrderBilledEvent informing the costumer was charged. Customer= {}, Total Amount={}", event.getCustomerId(), event.getTotalAmount());
        producer.producer(OrderBilledEvent.class.getSimpleName(), OrderBilledEvent.builder()
                .transactionId(UUID.randomUUID().toString())
                .id(UUID.randomUUID().toString())
                .build());
    }
}
