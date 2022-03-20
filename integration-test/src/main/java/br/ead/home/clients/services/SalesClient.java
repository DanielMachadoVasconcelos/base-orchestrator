package br.ead.home.clients.services;

import br.com.ead.sales.controllers.requests.PlaceOrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

@Log4j2
public class SalesClient {

    private final WebTestClient client;

    public SalesClient() {
        client = WebTestClient.bindToServer()
                .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("http://localhost:8082")
                .build();
    }

    public WebTestClient.ResponseSpec placeOrder(PlaceOrderRequest placeOrderRequest) {
        return client
                .post()
                .uri("/services/orders")
                .body(BodyInserters.fromValue(placeOrderRequest))
                .exchange();
    }
}
