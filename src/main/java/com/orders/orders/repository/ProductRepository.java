package com.orders.orders.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orders.orders.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
