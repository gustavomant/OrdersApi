package com.orders.orders.dtos;

import java.util.List;

public record CreateOrderDto (
    Long customerId,
    List<Long> productIds
) {}

