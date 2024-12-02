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

import com.orders.orders.dtos.CreateOrderDto;
import com.orders.orders.dtos.OrderDto;
import com.orders.orders.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Object> getAllOrders() {
        List<OrderDto> orderDtos = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(
        @RequestBody CreateOrderDto createOrderDto
    ) {
        orderService.createOrder(createOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
