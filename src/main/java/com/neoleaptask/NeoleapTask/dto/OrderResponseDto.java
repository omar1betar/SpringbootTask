package com.neoleaptask.NeoleapTask.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private long databaseId ;

    private UUID orderId = UUID.randomUUID();

    @NotNull
    @Size(min = 5, max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "NEW|PAID|CANCELLED")
    private String status;

    @NotNull
    @Size(min = 5, max = 255)
    private String productName;

    public long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(long databaseId) {
        this.databaseId = databaseId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public @NotNull @Size(min = 5, max = 255) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @Size(min = 5, max = 255) String description) {
        this.description = description;
    }

    public @NotNull @DecimalMin(value = "0.1", inclusive = true) BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @DecimalMin(value = "0.1", inclusive = true) BigDecimal amount) {
        this.amount = amount;
    }

    public @NotNull @Pattern(regexp = "NEW|PAID|CANCELLED") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull @Pattern(regexp = "NEW|PAID|CANCELLED") String status) {
        this.status = status;
    }

    public @NotNull @Size(min = 5, max = 255) String getProductName() {
        return productName;
    }

    public void setProductName(@NotNull @Size(min = 5, max = 255) String productName) {
        this.productName = productName;
    }
}

