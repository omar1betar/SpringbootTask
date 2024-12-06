package com.neoleaptask.NeoleapTask.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class OrderResponseDto {

    @NotNull
    @Size(min = 5, max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "NEW|PAID|CANCELLED")
    private String status;
}
