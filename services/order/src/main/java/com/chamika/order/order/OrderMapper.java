package com.chamika.order.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order mapToOrder(OrderCreateReqBody orderCreateReqBody) {
        return Order.builder()
                .reference(orderCreateReqBody.reference())
                .totalAmount(orderCreateReqBody.amount())
                .paymentMethod(orderCreateReqBody.paymentMethod())
                .customerId(orderCreateReqBody.customerId())
                .build();
    }
}
