package br.com.ead.sales.model;

import java.time.OffsetDateTime;

public interface Order {

    String getOrderId();
    String getPlaceOrderAmount();
    OffsetDateTime getCreatedAt();
}
