package com.chamika.notification.kafka;

import com.chamika.notification.kafka.order.OrderConfirmation;
import com.chamika.notification.kafka.payment.PaymentConfirmation;
import com.chamika.notification.notification.Notification;
import com.chamika.notification.notification.NotificationRepository;
import com.chamika.notification.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
//    private final EmailService emailService

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentNotification(PaymentConfirmation paymentConfirmation) {

        log.info("Consuming the message in `payment-topic` {} ", paymentConfirmation);

        // saving notification sent to MongoDB
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        // TODO: send the email

    }

    @KafkaListener(topics = "payment-topic")
    public void consumeOrderNotification(OrderConfirmation orderConfirmation) {

        log.info("Consuming the message in `payment-topic` {} ", orderConfirmation);

        // saving notification sent to MongoDB
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // TODO: send the email

    }






}
