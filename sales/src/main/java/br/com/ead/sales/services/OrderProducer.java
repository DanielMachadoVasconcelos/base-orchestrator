package br.com.ead.sales.services;

import br.com.ead.sales.entities.OrderEntity;
import br.com.ead.sales.events.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class OrderProducer {

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public Mono<OrderPublished> send(OrderEvent event) {
        var result =
                kafkaTemplate.send(event.getClass().getSimpleName(), event.eventData().getOrderId(), event);
        return Mono.fromFuture(result.completable())
                .map(OrderPublished::from)
                .doOnSuccess(item -> log.info("Order published to the topic! {}", item))
                .doOnError(error -> log.error("Failed to publish the order to the topic {}", event, error));

    }

    public record OrderPublished(String topic, String key, Integer partition, LocalDateTime timestamp, OrderEvent order) {
        public static OrderPublished from(SendResult<String, OrderEvent> result) {
            var record = result.getProducerRecord();
            var timestamp = Optional.ofNullable(record.timestamp())
                    .map(Instant::ofEpochMilli)
                    .map(LocalDateTime::from)
                    .orElseGet(LocalDateTime::now);

            return new OrderPublished(record.topic(),
                    record.key(),
                    record.partition(),
                    timestamp,
                    record.value());
        }
    }
}
