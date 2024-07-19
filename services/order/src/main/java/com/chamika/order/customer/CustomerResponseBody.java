package com.chamika.order.customer;

public record CustomerResponseBody(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
