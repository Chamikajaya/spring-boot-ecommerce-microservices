package com.chamika.notification.emails;

import com.chamika.notification.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chamika.notification.emails.EmailTemplateName.ORDER_CONFIRMATION;
import static com.chamika.notification.emails.EmailTemplateName.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Async  // since email sending should be non-blocking
    public void sendPaymentNotification(
            String to,
            String customerFullName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("chamika.21@cse.mrt.ac.lk");

        final String templateToUse = PAYMENT_CONFIRMATION.getTemplateToUse();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerFullName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = springTemplateEngine.process(templateToUse, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info("Payment notification email sent to {} ", to);

        } catch (MessagingException e) {
            log.warn("Failed to send email to {} ", to, e);
        }
    }


    @Async  // since email sending should be non-blocking
    public void sendOrderNotification(
            String to,
            String customerFullName,
            BigDecimal totalAmount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("chamika.21@cse.mrt.ac.lk");

        final String templateToUse = ORDER_CONFIRMATION.getTemplateToUse();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerFullName);
        variables.put("totalAmount", totalAmount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = springTemplateEngine.process(templateToUse, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            log.info("Order notification email sent to {} ", to);

        } catch (MessagingException e) {
            log.warn("Failed to send email to {} ", to, e);
        }


    }


}
