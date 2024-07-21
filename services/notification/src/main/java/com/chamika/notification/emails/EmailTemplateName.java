package com.chamika.notification.emails;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation");

    private final String templateToUse;
    private final String subject;


    EmailTemplateName(String template, String subject) {
        this.templateToUse = template;
        this.subject = subject;
    }


}
