package com.chamika.order.payment;

import com.chamika.order.customer.CustomerResponseBody;
import com.chamika.order.order.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequestBody(
        Integer id,  // for updating purposes ...

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Order ID is required")
        Integer orderId,

        @NotNull(message = "Order reference is required")
        String orderReference,  // custom order reference we created

        @NotNull(message = "Customer is required in payment request")
        CustomerResponseBody customer
) {
}
