package com.chamika.customer.customer;


import com.chamika.customer.customer.exception.ResourceNotFoundException;
import com.chamika.customer.customer.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerCreateReqBody request) {

        Customer customer = customerMapper.toCustomer(request);
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }


    public void updateCustomer(String id, CustomerCreateReqBody request) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with id " + id + " not found !")
        );

        if (request.firstName() != null && !request.firstName().isBlank()) {
            customer.setFirstName(request.firstName());
        }

        if (request.lastName() != null && !request.lastName().isBlank()) {
            customer.setLastName(request.lastName());
        }

        if (request.email() != null && !request.email().isBlank()) {
            customer.setEmail(request.email());
        }

        if (request.address() != null) {
            customer.setAddress(request.address());
        }

        customerRepository.save(customer);


    }


    public PageResponse<CustomerResponseBody> getAllCustomers(Integer page, Integer size) {

        // creating the pageable object
        Pageable pageable = PageRequest.of(
                page,
                size
        );

        Page<Customer> customerPage = customerRepository.findAllCustomers(pageable);

        List<CustomerResponseBody> customerResponseBodies = customerPage.stream()
                .map(customerMapper::toCustomerResponseBody)
                .toList();

        return new PageResponse<>(
                customerResponseBodies,
                customerPage.getNumber(),
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );

    }

    public CustomerResponseBody getCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with id " + id + " not found !")
        );

        return customerMapper.toCustomerResponseBody(customer);
    }

    public void deleteCustomer(String id) {

        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with id " + id + " not found !")
        );

        customerRepository.delete(customer);

    }

    public Boolean existsById(String id) {
        return customerRepository.existsById(id);
    }


}
