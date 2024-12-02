package com.orders.orders.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orders.orders.dtos.CreateProductDto;
import com.orders.orders.dtos.ProductDto;
import com.orders.orders.entity.Product;
import com.orders.orders.repository.ProductRepository;
import com.orders.orders.service.ProductService;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        CreateProductDto createProductDto = new CreateProductDto(
            "Mouse",
            BigDecimal.valueOf(16.99),
            BigInteger.valueOf(50)
        );

        productService.createProduct(createProductDto);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(captor.capture());

        Product captured = captor.getValue();
        assertEquals("Mouse", captured.getName());
        assertEquals(BigDecimal.valueOf(16.99), captured.getPrice());
        assertEquals(BigInteger.valueOf(50), captured.getStock());
    }

    @Test
    void getAllProducts() {
        List<Product> products = List.of(
            Product.builder().name("Mouse").price(BigDecimal.valueOf(16.99)).stock(BigInteger.valueOf(50)).build(),
            Product.builder().name("Teclado").price(BigDecimal.valueOf(16.99)).stock(BigInteger.valueOf(50)).build()
        );

        when(productRepository.findAll()).thenReturn(products);
        List<ProductDto> productDtos = productService.getAllProducts();

        assertEquals(2, productDtos.size());
        assertEquals("Mouse", productDtos.get(0).name());
        assertEquals("Teclado", productDtos.get(1).name());
    }
}
