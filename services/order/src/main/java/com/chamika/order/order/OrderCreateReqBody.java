package com.chamika.order.order;

import com.chamika.order.product.ProductPurchaseRequestBody;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Validated
public record OrderCreateReqBody(
        Integer id,
        @Positive(message = "Amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer ID is required")
        String customerId,
        @NotNull(message = "At least one product should be added")
        List<ProductPurchaseRequestBody> products
) {
}
