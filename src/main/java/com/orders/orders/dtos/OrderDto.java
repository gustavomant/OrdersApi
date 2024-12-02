package com.orders.orders.dtos;

import java.util.List;

public record OrderDto (
    Long customerId,
    List<ProductDto> products
) {}
