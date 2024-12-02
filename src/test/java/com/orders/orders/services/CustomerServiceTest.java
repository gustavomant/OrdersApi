package com.orders.orders.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orders.orders.dtos.CreateCustomerDto;
import com.orders.orders.dtos.CustomerDto;
import com.orders.orders.entity.Customer;
import com.orders.orders.repository.CustomerRepository;
import com.orders.orders.service.CustomerService;

public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(
            "Gustavo",
            "gustavo@evoi.com.br"
        );

        customerService.createCustomer(createCustomerDto);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(captor.capture());

        Customer captured = captor.getValue();
        assertEquals("Gustavo", captured.getName());
        assertEquals("gustavo@evoi.com.br", captured.getEmail());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = List.of(
            Customer.builder().name("Gustavo Mantovani").email("gustavo@evoi.com.br").build(),
            Customer.builder().name("John Doe").email("johndoe@email.com").build()
        );

        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDto> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        assertEquals("Gustavo Mantovani", result.get(0).name());
        assertEquals("gustavo@evoi.com.br", result.get(0).email());
        assertEquals("John Doe", result.get(1).name());
        assertEquals("johndoe@email.com", result.get(1).email());
    }
}
