package com.orders.orders.dtos;

import java.math.BigDecimal;
import java.math.BigInteger;

public record CreateProductDto (
    String name,
    BigDecimal price,
    BigInteger stock
) {}