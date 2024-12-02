package com.orders.orders.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.orders.dtos.CreateOrderDto;
import com.orders.orders.dtos.OrderDto;
import com.orders.orders.dtos.ProductDto;
import com.orders.orders.entity.Customer;
import com.orders.orders.entity.Order;
import com.orders.orders.entity.Product;
import com.orders.orders.repository.CustomerRepository;
import com.orders.orders.repository.OrderRepository;
import com.orders.orders.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;
    
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
            .stream()
            .map(order -> {
                List<ProductDto> productDtos = order.getProducts()
                    .stream()
                    .map(product -> new ProductDto(
                        product.getName(),
                        product.getPrice(),
                        product.getStock()
                    ))
                    .collect(Collectors.toList());
    
                return new OrderDto(
                    order.getCustomer().getId(),
                    productDtos 
                );
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public void createOrder(CreateOrderDto createOrderDto) {
        Customer customer = customerRepository.findById(createOrderDto.customerId()).orElseThrow();

        Map<Long, Long> productQuantities = createOrderDto.productIds().stream()
        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        
        List<Product> products = productRepository.findAllById(productQuantities.keySet());
        products.forEach(product -> {
            long quantity = productQuantities.getOrDefault(product.getId(), 0L);
            product.deductStock((int) quantity);
        });
        productRepository.saveAll(products);

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(new HashSet<>(products));
        order.calcTotalAmount();
        order.setStatusToPending();

        orderRepository.save(order);
    }
}
