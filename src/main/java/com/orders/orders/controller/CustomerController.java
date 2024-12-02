package com.orders.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orders.orders.dtos.CreateCustomerDto;
import com.orders.orders.dtos.CustomerDto;
import com.orders.orders.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customerDtos);
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(
        @RequestBody @Validated CreateCustomerDto createCustomerDto
    ) {
        customerService.createCustomer(createCustomerDto);    
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}
