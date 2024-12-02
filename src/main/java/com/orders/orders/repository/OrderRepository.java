package com.orders.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orders.orders.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{}
