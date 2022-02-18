package br.com.ead.sales.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record PlaceOrderRequest(@NotEmpty String id, @Min(0) @NotNull Integer amount) {
}
