package com.chamika.customer.customer;

import com.chamika.customer.customer.shared.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerCreateReqBody request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> updateCustomer(
            @PathVariable String id,
            @RequestBody @Valid CustomerCreateReqBody request) {

        customerService.updateCustomer(id, request);
        return ResponseEntity.accepted().build();

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResponse<CustomerResponseBody>> getAllCustomers(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "8", required = false) Integer size
    ) {
        return ResponseEntity.ok(customerService.getAllCustomers(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerResponseBody> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> existsById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.existsById(id));
    }


}
