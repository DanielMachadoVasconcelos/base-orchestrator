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

        //given: A place order request
        var expectedOrderId = UUID.randomUUID().toString();
        var expectedOrderAmount = 100;
        var request = new PlaceOrderRequest(expectedOrderId, expectedOrderAmount);

        //when: placing the order
        var response = client.placeOrder(request);

        //then: the response is successful
        response.expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .expectBody().jsonPath("place_order_amount").isEqualTo(expectedOrderAmount);

        //and: package label is generated

        //and: a payment reservation is created

    }
}
