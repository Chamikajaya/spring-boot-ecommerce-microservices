package com.chamika.order.kafka;

import com.chamika.order.customer.CustomerResponseBody;
import com.chamika.order.order.PaymentMethod;
import com.chamika.order.product.ProductPurchaseResponseBody;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponseBody customer, // customer details
        List<ProductPurchaseResponseBody> products // products purchased
) {
}
