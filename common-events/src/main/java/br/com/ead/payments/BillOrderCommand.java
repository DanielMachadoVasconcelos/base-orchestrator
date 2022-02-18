package br.com.ead.payments;

import br.com.ead.commons.BaseCommand;
import br.com.ead.sales.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillOrderCommand extends BaseCommand {
    String customerId;
    PaymentMethod paymentMethod;
    String currency;
    Double totalAmount;
}
