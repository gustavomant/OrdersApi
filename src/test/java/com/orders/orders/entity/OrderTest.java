package com.orders.orders.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

    private Order order;
    private Customer customer;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        product1 = new Product();
        product1.setId(1L);
        product1.setPrice(BigDecimal.valueOf(50.00));

        product2 = new Product();
        product2.setId(2L);
        product2.setPrice(BigDecimal.valueOf(30.00));

        order = new Order();
        order.setCustomer(customer);
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);
        order.setProducts(products);
    }

    @Test
    void testCalcTotalAmount_ShouldCalculateCorrectTotal() {
        order.calcTotalAmount();

        assertEquals(BigDecimal.valueOf(80.00), order.getTotalAmount(), "Total amount should be correctly calculated");
    }

    @Test
    void testSetStatusToPending_ShouldSetStatusToPending() {
        order.setStatusToPending();

        assertEquals(Order.Status.PENDING, order.getStatus(), "Status should be PENDING");
    }

    @Test
    void testSetStatusToPaid_ShouldSetStatusToPaid() {
        order.setStatusToPaid();

        assertEquals(Order.Status.PAID, order.getStatus(), "Status should be PAID");
    }

    @Test
    void testSetStatusToShipped_ShouldSetStatusToShipped() {
        order.setStatusToShipped();

        assertEquals(Order.Status.SHIPPED, order.getStatus(), "Status should be SHIPPED");
    }

    @Test
    void testSetStatusToDelivered_ShouldSetStatusToDelivered() {
        order.setStatusToDelivered();

        assertEquals(Order.Status.DELIVERED, order.getStatus(), "Status should be DELIVERED");
    }

    @Test
    void testSetStatusToCancelled_ShouldSetStatusToCancelled() {
        order.setStatusToCancelled();

        assertEquals(Order.Status.CANCELLED, order.getStatus(), "Status should be CANCELLED");
    }
}

