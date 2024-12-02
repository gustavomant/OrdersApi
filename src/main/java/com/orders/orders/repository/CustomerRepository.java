package com.orders.orders.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.orders.orders.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
}
