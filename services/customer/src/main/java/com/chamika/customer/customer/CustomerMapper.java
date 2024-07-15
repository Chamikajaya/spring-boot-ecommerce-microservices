package com.chamika.customer.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerCreateReqBody request) {

        if (request == null) {
            return null;
        }

        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(
                        Address.builder()
                                .street(request.address().getStreet())
                                .houseNumber(request.address().getHouseNumber())
                                .zipCode(request.address().getZipCode())
                                .build()
                )
                .build();
    }

}