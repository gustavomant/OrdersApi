package com.orders.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orders.orders.dtos.CreateProductDto;
import com.orders.orders.dtos.ProductDto;
import com.orders.orders.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(
        @RequestBody CreateProductDto createProductDto
    ) {
        productService.createProduct(createProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
