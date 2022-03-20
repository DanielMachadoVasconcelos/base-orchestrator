package br.com.ead.sales.controllers;

import br.com.ead.sales.entities.OrderEntity;
import br.com.ead.sales.controllers.requests.PlaceOrderRequest;
import br.com.ead.sales.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/services/")
public class OrderController {

    private OrderService orderService;

    @PostMapping(path = "/orders")
    private Mono<OrderEntity> pokeServiceUri(@RequestBody PlaceOrderRequest request) {
        return orderService.placeOrder(request);
    }
}
