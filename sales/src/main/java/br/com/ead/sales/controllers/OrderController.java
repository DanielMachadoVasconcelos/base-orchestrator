package br.com.ead.sales.controllers;

import br.com.ead.sales.model.PlaceOrderRequest;
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

    @PostMapping(path = "/orders")
    private Mono<String> pokeServiceUri(@RequestBody PlaceOrderRequest request) {
        return Mono.fromCallable(() -> request.toString());
    }
}
