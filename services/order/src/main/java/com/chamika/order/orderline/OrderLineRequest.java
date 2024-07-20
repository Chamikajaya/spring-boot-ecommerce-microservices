package com.chamika.order.orderline;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record OrderLineRequest(
        @NotNull(message = "Order ID is required")
        Integer orderId,
        @NotNull(message = "Product ID is required")
        Integer productId,
        @NotNull(message = "Quantity is required")
        Integer quantity
) {
}
