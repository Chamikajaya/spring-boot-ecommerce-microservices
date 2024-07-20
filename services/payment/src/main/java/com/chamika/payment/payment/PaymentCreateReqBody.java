package com.chamika.payment.payment;

import com.chamika.payment.customer.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record PaymentCreateReqBody(
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
        Customer customer
) {
}
