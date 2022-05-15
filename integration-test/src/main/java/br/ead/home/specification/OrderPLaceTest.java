package br.ead.home.specification;

import br.com.ead.sales.OrderLine;
import br.com.ead.sales.PaymentMethod;
import br.com.ead.sales.commands.OrderPLaceCommand;
import br.ead.home.clients.services.SalesClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Set;
import java.util.UUID;

public class OrderPLaceTest {

    private final SalesClient client = new SalesClient();

    @Test
    @DisplayName("Should be able to place a order when user requesting a purchase")
    void shouldPlaceOrderWhenRequested() {

        //given: A place order command
        var expectedOrderAmount = 100;
        var expectedCustomerId = UUID.randomUUID().toString();
        var expectedProductId = UUID.randomUUID().toString();
        var orderLine = OrderLine.builder()
                .productId(expectedProductId)
                .quantity(1)
                .unitPrice(20.0D).build();

        var command = new OrderPLaceCommand(expectedOrderAmount,
                expectedCustomerId,
                PaymentMethod.CREDIT_CARD,
                "USD",
                Set.of(orderLine));

        //when: placing the order
        var response = client.placeOrder(command);

        //then: the response is successful
        response.expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .expectBody().jsonPath("id").exists();

        //and: package label is generated

        //and: a payment reservation is created

    }
}
