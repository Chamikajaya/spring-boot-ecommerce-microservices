package com.chamika.notification.kafka.order;

import java.math.BigDecimal;

public record Product(
        Integer productId,
        String productName,
        String productDescription,
        Integer quantity,
        BigDecimal price
) {
}
