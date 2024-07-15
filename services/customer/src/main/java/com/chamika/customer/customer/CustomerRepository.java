package com.chamika.customer.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query(
            """
            SELECT customer
            FROM Customer customer
            """
    )
    Page<Customer> findAllCustomers(Pageable pageable);



}
