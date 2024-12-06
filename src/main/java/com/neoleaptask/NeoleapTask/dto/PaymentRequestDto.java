package com.neoleaptask.NeoleapTask.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PaymentRequestDto {

    @NotNull
    private Long orderId;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal amount;

    public @NotNull Long getOrderId() {
        return orderId;
    }

    public void setOrderId(@NotNull Long orderId) {
        this.orderId = orderId;
    }

    public @NotNull @DecimalMin(value = "0.1", inclusive = true) BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @DecimalMin(value = "0.1", inclusive = true) BigDecimal amount) {
        this.amount = amount;
    }
}
