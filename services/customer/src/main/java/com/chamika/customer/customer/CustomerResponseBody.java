package com.chamika.customer.customer;

public record CustomerResponseBody(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
