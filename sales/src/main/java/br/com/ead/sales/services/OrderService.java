package br.com.ead.sales.services;

import br.com.ead.sales.entities.Order;
import br.com.ead.sales.model.PlaceOrderRequest;
import br.com.ead.sales.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository repository;

    public Mono<Order> placeOrder(PlaceOrderRequest request){
        return repository.save(
                Order.builder()
                        .orderId(Optional.ofNullable(request.id())
                                .filter(String::isBlank)
                                .orElseGet(UUID.randomUUID()::toString))
                        .placeOrderAmount(request.amount())
                        .createdAt(OffsetDateTime.now())
                        .build());
    }
}
