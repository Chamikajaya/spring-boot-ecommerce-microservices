package com.chamika.product.product;

import java.math.BigDecimal;

public record ProductPurchaseResponseBody(
        Integer productId,
        String productName,

        String productDescription,

        Integer quantity,
        BigDecimal price
) {
}
