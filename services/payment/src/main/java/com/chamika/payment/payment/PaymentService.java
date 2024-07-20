package com.chamika.payment.payment;

import com.chamika.payment.kafka.PaymentNotification;
import com.chamika.payment.kafka.PaymentNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentNotificationProducer paymentNotificationProducer;

    public Integer createPayment(PaymentCreateReqBody paymentCreateReqBody) {

        Payment payment = paymentRepository.save(paymentMapper.toPayment(paymentCreateReqBody));

        // sending the payment notification to Kafka - "payment-topic"
        paymentNotificationProducer.sendPaymentNotification(

                new PaymentNotification(
                        paymentCreateReqBody.orderReference(),
                        paymentCreateReqBody.amount(),
                        paymentCreateReqBody.paymentMethod(),
                        paymentCreateReqBody.customer().firstName(),
                        paymentCreateReqBody.customer().lastName(),
                        paymentCreateReqBody.customer().email()
                )
        );

        return payment.getId();

    }
}
