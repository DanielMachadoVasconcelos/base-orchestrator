package br.com.ead.sales.services;

import br.com.ead.sales.entities.OrderEntity;
import br.com.ead.sales.controllers.requests.PlaceOrderRequest;
import br.com.ead.sales.events.EventMetadata;
import br.com.ead.sales.events.OrderEvent;
import br.com.ead.sales.repositories.OrderRepository;
import br.com.ead.sales.events.OrderEventType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.ead.sales.services.OrderProducer.OrderPublished;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository repository;
    private OrderProducer producer;

    public Mono<OrderEntity> placeOrder(PlaceOrderRequest request) {

        var orderId = Optional.ofNullable(request.id())
                .filter(String::isBlank)
                .orElseGet(UUID.randomUUID()::toString);

        var order = OrderEntity.builder()
                .orderId(orderId)
                .placeOrderAmount(request.amount())
                .createdAt(OffsetDateTime.now())
                .build();

        var event = new OrderEvent(EventMetadata.builder()
                .eventType(OrderEventType.ORDER_PLACED.name())
                .createdAt(OffsetDateTime.now())
                .build(), order);

        return producer.send(event)
                        .map(OrderPublished::order)
                        .map(OrderEvent::eventData)
                        .map(item -> (OrderEntity) item)
                        .flatMap(repository::save)
                        .doOnError(error -> log.error("There were a error while saving the order into database!", error))
                        .doOnSuccess(item -> log.info("Order was successful saved into database! {}", item));
    }
}
