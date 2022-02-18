package br.com.ead.payments.events;

import br.com.ead.payments.model.OrderLine;
import br.com.ead.payments.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPLacedEvent extends BaseEvent {

    String customerId;
    Date createdAt;
    PaymentMethod paymentMethod;
    String currency;
    Set<OrderLine> orderLines;

}
