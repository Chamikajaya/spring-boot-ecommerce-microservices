package com.chamika.notification.notification;

import com.chamika.notification.kafka.order.OrderConfirmation;
import com.chamika.notification.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    private String id;

    private NotificationType notificationType;

    private LocalDateTime localDateTime;

    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;







}
