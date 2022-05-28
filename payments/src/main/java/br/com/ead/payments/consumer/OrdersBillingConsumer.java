package br.com.ead.payments.consumer;

import br.com.ead.payments.BillOrderCommand;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class OrdersBillingConsumer {

    @KafkaListener(topics = "BillOrderCommand", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload BillOrderCommand event) {
        log.info("Received an Bill Order Command. {}", event);
        log.info("TODO: should process the billing command.");
        log.info("TODO: should save the billing data in the database.");
        log.info("TODO: should emit an event informing the costumer was charged.");
    }
}
