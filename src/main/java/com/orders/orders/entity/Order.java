package com.orders.orders.entity;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public enum Status {
        PENDING,
        PAID,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public void calcTotalAmount() {
        BigDecimal total = products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.setTotalAmount(total);
    }

    public void setStatusToPending() {
        setStatus(Status.PENDING);
    }

    public void setStatusToPaid() {
        setStatus(Status.PAID);
    }

    public void setStatusToShipped() {
        setStatus(Status.SHIPPED);
    }
    
    public void setStatusToDelivered() {
        setStatus(Status.DELIVERED);
    }

    public void setStatusToCancelled() {
        setStatus(Status.CANCELLED);
    }
}
