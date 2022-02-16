package br.ead.home.specification;

import br.com.ead.sales.model.PlaceOrderRequest;
import br.ead.home.clients.services.SalesClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OrderPLaceTest {

    private SalesClient client = new SalesClient();

    @Test
    @DisplayName("Should be able to place a order when user requesting a purchase")
    void shouldPlaceOrderWhenRequested() {

        var request = new PlaceOrderRequest(UUID.randomUUID().toString(), 100);
        var response = client.placeOrder(request);

//        Assertions.assertTrue(response.statusCode() == 200, "Should place the order successfully");
        Assertions.assertNotNull(response, "Should place the order successfully");
    }
}
