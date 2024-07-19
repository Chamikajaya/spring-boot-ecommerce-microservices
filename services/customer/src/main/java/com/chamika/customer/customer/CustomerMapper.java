package com.chamika.customer.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {


    // *  Converts the CustomerCreateReqBody DTO to a Customer domain object.
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


    // * Converts the Customer domain object to a CustomerResponseBody DTO.
    public CustomerResponseBody toCustomerResponseBody(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerResponseBody(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );


    }

}
