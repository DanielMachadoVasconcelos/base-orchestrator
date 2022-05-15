package br.com.ead.payments.consumers;

import br.com.ead.sales.OrderPLacedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class OrderPLaceConsumer {

    @KafkaListener(topics = "OrderPLacedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload OrderPLacedEvent event) {
        log.info("Order PLaced Event received. {}", event);
    }
}
