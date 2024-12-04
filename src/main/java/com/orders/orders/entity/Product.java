package com.orders.orders.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.orders.orders.exception.InsufficientStockException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private BigInteger stock;

    public void deductStock() {
        if (this.stock.compareTo(BigInteger.ONE) >= 0) {
            this.stock = this.stock.subtract(BigInteger.ONE);
        } else {
            throw new InsufficientStockException();
        }
    }

    public void deductStock(int amount) {
        if (this.stock.compareTo(BigInteger.valueOf(amount)) >= 0) {
            this.stock = this.stock.subtract(BigInteger.valueOf(amount));
        } else {
            throw new InsufficientStockException();
        }
    }
    
}
