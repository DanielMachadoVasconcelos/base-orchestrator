package br.com.ead.sales;


import br.com.ead.commons.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPLacedEvent extends BaseEvent {

    @NotBlank
    String customerId;

    @PastOrPresent
    Date createdAt;

    @NotNull
    PaymentMethod paymentMethod;

    @NotBlank
    @Max(value = 3)
    @Min(value = 3)
    String currency;

    @NotEmpty
    Set<OrderLine> orderLines;

}
