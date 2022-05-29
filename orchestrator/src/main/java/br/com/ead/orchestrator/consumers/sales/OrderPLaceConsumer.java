package br.com.ead.orchestrator.consumers.sales;

import br.com.ead.orchestrator.producer.OrderEventProducer;
import br.com.ead.payments.BillOrderCommand;
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

    private final OrderEventProducer producer;

    @KafkaListener(topics = "OrderPLacedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload OrderPLacedEvent event) {
        log.info("Order PLaced Event received. {}", event);
        log.warn("TODO: should persist the OrderPLacedEvent into the order saga data source.");

        var totalAmount = event.getOrderLines().stream()
                                  .map(line -> line.getQuantity() * line.getUnitPrice())
                                  .reduce(Double::sum)
                                  .orElse(0D);

        var command = new BillOrderCommand(event.getCustomerId(),
                event.getPaymentMethod(),
                event.getCurrency(),
                totalAmount);

        log.info("Emitting an command to billing service to charge the customer the order total amount. Customer= {}, Total Amount={}", event.getCustomerId(), totalAmount);
        producer.producer(BillOrderCommand.class.getSimpleName(), command);
    }
}
