package com.chamika.order.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order mapToOrder(OrderCreateReqBody orderCreateReqBody) {
        return Order.builder()
                .totalAmount(orderCreateReqBody.amount())
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
