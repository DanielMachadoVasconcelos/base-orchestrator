package br.com.ead.payments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {

    @NotBlank
    String productId;

    @Min(0)
    int quantity;

    @Min(0)
    double unitPrice;
}
