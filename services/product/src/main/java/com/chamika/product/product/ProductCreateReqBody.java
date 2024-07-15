package com.chamika.product.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateReqBody(
        Integer id,

        @NotNull(message = "Product name is required")
        String name,

        @NotNull(message = "Product description is required")
        String description,

        @NotNull(message = "Quantity in stock is required")
        @Positive(message = "Quantity in stock should be positive")
        Integer quantityInStock,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price should be positive")
        BigDecimal unitPrice,

        @NotNull(message = "Category id is required")
        Integer categoryId

) {
}
