package br.com.ead.orchestrator.consumers.payments;

import br.com.ead.payments.OrderBilledEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class OrderBilledConsumer {

    @KafkaListener(topics = "OrderBilledEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload OrderBilledEvent event) {
        log.info("Order Billed Event received. {}", event);
        log.info("TODO: should persist the OrderBilledEvent into the order saga.");
        log.info("TODO: should emit an command to warehouse to reserve or label the order items.");
    }
}
