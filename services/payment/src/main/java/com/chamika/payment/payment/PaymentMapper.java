package com.chamika.payment.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentCreateReqBody paymentCreateReqBody) {

        if (paymentCreateReqBody == null) {
            return null;
        }

        // no custom payment ref
        return Payment.builder()
                .paymentMethod(paymentCreateReqBody.paymentMethod())
                .amount(paymentCreateReqBody.amount())
                .orderId(paymentCreateReqBody.orderId())
                .build();
    }
}
