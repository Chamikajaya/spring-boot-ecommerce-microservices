package com.chamika.order.product;

import java.math.BigDecimal;

public record ProductPurchaseResponseBody(
        Integer productId,
        String productName,

        String productDescription,

        Integer quantity,
        BigDecimal price
) {
}
