package com.orders.orders.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.orders.dtos.CreateCustomerDto;
import com.orders.orders.dtos.CustomerDto;
import com.orders.orders.entity.Customer;
import com.orders.orders.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
            .stream()
            .map((customer) -> {
                return new CustomerDto(customer.getName(), customer.getEmail());
            })
            .collect(Collectors.toList());
    }

    public void createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = Customer.builder()
            .name(createCustomerDto.name())
            .email(createCustomerDto.email())
            .build();
        
        customerRepository.save(customer);
    }
}
