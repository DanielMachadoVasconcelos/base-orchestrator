package br.ead.home.clients.services;

import br.com.ead.sales.commands.OrderPLaceCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

import java.time.Duration;
import java.util.Optional;

import static org.springframework.test.web.reactive.server.WebTestClient.*;

@Log4j2
public class SalesClient {

    private final WebTestClient client;

    public SalesClient() {
        var salesHost = System.getenv("SALES_HOST");
        var baseUrl = "http://%s:8082".formatted(Optional.ofNullable(salesHost).orElse("localhost"));
        log.debug("Sales Host: {}", salesHost);
        log.debug("Sales base host: {}", baseUrl);
        client = bindToServer()
                .responseTimeout(Duration.ofSeconds(15))
                .filter(ExchangeFilterFunctions.basicAuthentication("admin", "password"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(baseUrl)
                .build();
    }

    public ResponseSpec placeOrder(OrderPLaceCommand command) {
        log.debug("Placing and order: {}", command);
        return client
                .post()
                .uri("/api/v1/orders")
                .body(BodyInserters.fromValue(command))
                .exchange();
    }
}
