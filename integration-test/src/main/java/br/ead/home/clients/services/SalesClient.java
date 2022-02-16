package br.ead.home.clients.services;

import br.com.ead.sales.model.PlaceOrderRequest;
import br.ead.home.clients.MapConverter;
import br.ead.home.clients.Response;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
public class SalesClient {

    private final WebClient client;

    public SalesClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        client = WebClient.builder()
                .baseUrl("http://sales:8082")
                .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
        ;
    }

    public String placeOrder(PlaceOrderRequest placeOrderRequest) {
        return client.post()
                .uri("/services/orders")
                .body(BodyInserters.fromValue(placeOrderRequest))
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .block();
    }
}
