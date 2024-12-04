package com.orders.orders.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.orders.orders.exception.InsufficientStockException;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setStock(BigInteger.valueOf(5));
    }

    @Test
    void testDeductStock_ShouldReduceStock_WhenSufficientStockAvailable() {
        BigInteger initialStock = product.getStock();
        product.deductStock();
        
        assertEquals(initialStock.subtract(BigInteger.ONE), product.getStock(), "Stock should decrease by 1");
    }

    @Test
    void testDeductStock_ShouldThrowException_WhenInsufficientStock() {
        product.setStock(BigInteger.ZERO);
        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> {
            product.deductStock();
        });

        assertNotNull(exception, "Exception should be thrown when stock is insufficient");
    }

    @Test
    void testDeductStock_ShouldReduceStock_WhenSufficientStockForAmount() {
        int amount = 3;
        BigInteger initialStock = product.getStock();

        product.deductStock(amount);

        assertEquals(initialStock.subtract(BigInteger.valueOf(amount)), product.getStock(), "Stock should decrease by the specified amount");
    }

    @Test
    void testDeductStock_ShouldThrowException_WhenInsufficientStockForAmount() {
        int amount = 6;
        product.setStock(BigInteger.valueOf(5));

        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> {
            product.deductStock(amount);
        });

        assertNotNull(exception, "Exception should be thrown when requested amount exceeds stock");
    }
}

