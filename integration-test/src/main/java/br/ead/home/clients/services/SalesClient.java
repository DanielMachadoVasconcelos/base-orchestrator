package br.ead.home.clients.services;

import br.com.ead.sales.model.PlaceOrderRequest;
import br.ead.home.clients.HttpClient;
import br.ead.home.clients.HttpHeaders;
import br.ead.home.clients.MapConverter;
import br.ead.home.clients.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import java.util.UUID;

import static br.ead.home.clients.MapConverter.asJson;
import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
public class SalesClient {

    private HttpClient httpClient = new HttpClient("localhost", 8082,
            HttpHeaders.createBasicAuthHeader("admin", "password"));

    public Response placeOrder(PlaceOrderRequest placeOrderRequest) {
        var body = MapConverter.toClass(placeOrderRequest, PlaceOrderRequest.class);
        var request = new BasicHttpEntityEnclosingRequest("POST", "/services/orders");
        request.setEntity(new StringEntity(asJson(body), UTF_8));
        return httpClient.call(request);
    }

    public static String randomIdempotencyId() {
        return UUID.randomUUID().toString();
    }
}
