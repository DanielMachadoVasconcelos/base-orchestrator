package br.com.ead.sales.services;

import br.com.ead.sales.entities.Order;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Log4j2
@Service
@AllArgsConstructor
public class OrderProducer {

    private KafkaTemplate<String, Order> kafkaTemplate;

    public void send(Order order) {
        kafkaTemplate.send(order.getClass().getSimpleName(), order.getOrderId(), order)
                .addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Failed to publish the order to the topic {}", order, ex);
                    }

                    @Override
                    public void onSuccess(SendResult<String, Order> result) {
                        log.info("Order published to the topic! {}", order);
                    }
                });
    }
}
