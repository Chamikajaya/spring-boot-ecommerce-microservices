package com.chamika.notification.kafka;

import com.chamika.notification.emails.EmailService;
import com.chamika.notification.kafka.order.OrderConfirmation;
import com.chamika.notification.kafka.payment.PaymentConfirmation;
import com.chamika.notification.notification.Notification;
import com.chamika.notification.notification.NotificationRepository;
import com.chamika.notification.notification.NotificationType;
import jakarta.mail.MessagingException;
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
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {

        log.info("Consuming the message in `payment-topic` {} ", paymentConfirmation);

        // saving notification sent to MongoDB
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        // send the email
        emailService.sendPaymentNotification(
                paymentConfirmation.customerEmail(),
                paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname(),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderNotification(OrderConfirmation orderConfirmation) throws MessagingException {

        log.info("Consuming the message in `payment-topic` {} ", orderConfirmation);

        // saving notification sent to MongoDB
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // send the email
        emailService.sendOrderNotification(
                orderConfirmation.customer().email(),
                orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

        log.info("Email sent for order confirmation 😊😊😊 for order reference: {}", orderConfirmation.orderReference());

    }


}
