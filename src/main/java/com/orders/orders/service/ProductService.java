package com.orders.orders.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.orders.dtos.CreateProductDto;
import com.orders.orders.dtos.ProductDto;
import com.orders.orders.entity.Product;
import com.orders.orders.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    
    public void createProduct(CreateProductDto createProductDto) {
        Product product = Product.builder()
            .name(createProductDto.name())
            .price(createProductDto.price())
            .stock(createProductDto.stock())
            .build();

        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll() 
            .stream()
            .map((product) -> {
                return new ProductDto(
                    product.getName(),
                    product.getPrice(),
                    product.getStock()    
                );
            })
            .collect(Collectors.toList());
    }
}
