package com.chamika.order.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequestBody(
        @NotNull(message = "Product ID is required")
        Integer productId,

        @NotNull(message = "Quantity is required")
        Integer quantity
) {
}
