package com.orders.orders.dtos;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ProductDto (
    String name,
    BigDecimal price,
    BigInteger stock
) {}
