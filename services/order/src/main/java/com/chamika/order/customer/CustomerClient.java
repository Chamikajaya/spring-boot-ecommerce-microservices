package com.chamika.order.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer",
        url = "${application.config.customer-url}"  // defined in configurations/order.yaml
)
public interface CustomerClient {
    @GetMapping("/{id}")
    Optional<CustomerResponseBody> getCustomerById(@PathVariable("id") String customerId);
}
