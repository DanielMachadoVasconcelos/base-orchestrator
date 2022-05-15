package br.com.ead.sales.model;

import br.com.ead.sales.OrderLine;
import br.com.ead.sales.OrderPLacedEvent;
import br.com.ead.sales.PaymentMethod;
import br.com.ead.sales.agregate.AggregateRoot;
import br.com.ead.sales.commands.OrderPLaceCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderAggregate extends AggregateRoot {

    Set<OrderLine> orderLines;
    PaymentMethod paymentMethod;
    String currency;
    double totalAmount;

    public OrderAggregate(OrderPLaceCommand command) {
        raiseEvent(OrderPLacedEvent.builder()
                .id(command.getId())
                .customerId(command.getCustomerId())
                .paymentMethod(command.getPaymentMethod())
                .createdAt(Date.from(Instant.now()))
                .orderLines(command.getOrderLines())
                .currency(command.getCurrency())
                .build());
    }

    public void apply(OrderPLacedEvent event) {
        this.id = event.getId();
        this.currency = event.getCurrency();
        this.paymentMethod = event.getPaymentMethod();
        this.orderLines = event.getOrderLines();
        this.totalAmount = event.getOrderLines().stream()
                .map(orderLine -> orderLine.getQuantity() * orderLine.getUnitPrice())
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
