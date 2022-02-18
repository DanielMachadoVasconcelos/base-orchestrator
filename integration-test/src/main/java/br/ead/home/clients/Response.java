package br.ead.home.clients;

import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public record Response(int statusCode, Mono<String> body, Map<String, String> headers) {

    public static Mono<Response> from(ClientResponse response) {
        return Mono.fromSupplier(() -> new Response(response.rawStatusCode(),
                response.bodyToMono(String.class),
                response.headers().asHttpHeaders().toSingleValueMap()));
    }
}
