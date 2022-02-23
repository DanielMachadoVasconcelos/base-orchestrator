package br.ead.home.specification;

import br.com.ead.sales.model.PlaceOrderRequest;
import br.ead.home.clients.services.SalesClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.UUID;

public class OrderPLaceTest {

    private final SalesClient client = new SalesClient();

    @Test
    @DisplayName("Should be able to place a order when user requesting a purchase")
    void shouldPlaceOrderWhenRequested() {

        var request = new PlaceOrderRequest(UUID.randomUUID().toString(), 100);
        client.placeOrder(request)
                .expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .expectBody().jsonPath("place_order_amount").isEqualTo(100);
    }
}
