package com.chamika.order.order;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderMapper {

    public Order mapToOrder(OrderCreateReqBody orderCreateReqBody, BigDecimal totalAmount) {
        return Order.builder()
                .totalAmount(totalAmount)
                .paymentMethod(orderCreateReqBody.paymentMethod())
                .customerId(orderCreateReqBody.customerId())
                .build();
    }

    public OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()

        );
    }
}
