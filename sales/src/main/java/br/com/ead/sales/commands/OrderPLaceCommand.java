package br.com.ead.sales.commands;

import br.com.ead.sales.OrderLine;
import br.com.ead.sales.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class OrderPLaceCommand extends BaseCommand {

    @Min(0)
    double amount;

    @NotBlank
    String customerId;

    @NotNull
    PaymentMethod paymentMethod;

    @NotNull
    String currency;

    @NotEmpty
    Set<OrderLine> orderLines;
}
