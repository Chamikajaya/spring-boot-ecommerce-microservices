package com.chamika.product.product;

import java.math.BigDecimal;

public record ProductResponseBody(
        Integer id,
        String name,
        String description,
        Integer quantityInStock,
        BigDecimal unitPrice,
        Integer categoryId,
        String categoryName,
        String categoryDescription

) {
}
