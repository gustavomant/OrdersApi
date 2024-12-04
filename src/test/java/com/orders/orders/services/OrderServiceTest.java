package com.orders.orders.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orders.orders.dtos.CreateOrderDto;
import com.orders.orders.dtos.OrderDto;
import com.orders.orders.dtos.ProductDto;
import com.orders.orders.entity.Customer;
import com.orders.orders.entity.Order;
import com.orders.orders.entity.Product;
import com.orders.orders.repository.CustomerRepository;
import com.orders.orders.repository.OrderRepository;
import com.orders.orders.repository.ProductRepository;
import com.orders.orders.service.OrderService;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders_returnsOrderDtos() {
        Customer customer = new Customer();
        customer.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setStock(BigInteger.valueOf(100));

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(Set.of(product));

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDto> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        OrderDto orderDto = result.get(0);
        assertEquals(1L, orderDto.customerId());
        assertEquals(1, orderDto.products().size());

        ProductDto productDto = orderDto.products().get(0);
        assertEquals("Product 1", productDto.name());
        assertEquals(BigDecimal.valueOf(10.0), productDto.price());
        assertEquals(BigInteger.valueOf(100), productDto.stock());
    }

    @Test
    void testCreateOrder_throwsExceptionIfCustomerNotFound() {
        CreateOrderDto createOrderDto = new CreateOrderDto(1L, List.of(1L, 2L));
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> orderService.createOrder(createOrderDto));
    }

    @Test
    void testCreateOrder_createsOrderSuccessfully() {
        Customer customer = new Customer();
        customer.setId(1L);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setPrice(BigDecimal.valueOf(10.0));
        product1.setStock(BigInteger.valueOf(100));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setPrice(BigDecimal.valueOf(10.0));
        product2.setStock(BigInteger.valueOf(50));

        CreateOrderDto createOrderDto = new CreateOrderDto(1L, List.of(1L, 2L, 2L));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findAllById(Set.of(1L, 2L)))
            .thenReturn(List.of(product1, product2));

        orderService.createOrder(createOrderDto);

        verify(productRepository).saveAll(anyList());
        verify(orderRepository).save(any(Order.class));

        assertEquals(BigInteger.valueOf(99), product1.getStock());
        assertEquals(BigInteger.valueOf(48), product2.getStock());
    }
}

