package br.com.ead.sales.events;

import br.com.ead.sales.model.Order;

public record OrderEvent(EventMetadata eventMetadata, Order eventData) {}
