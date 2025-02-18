package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "supplier_orders")
public class SupplierOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierOrderId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    private double totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatus orderStatus;

    public SupplierOrder() {
    }

    public SupplierOrder(Long supplierOrderId, Supplier supplier, double totalPrice, LocalDateTime createdAt, OrderStatus orderStatus) {
        this.supplierOrderId = supplierOrderId;
        this.supplier = supplier;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SupplierOrder that)) return false;
        return Double.compare(totalPrice, that.totalPrice) == 0 && Objects.equals(supplierOrderId, that.supplierOrderId) && Objects.equals(supplier, that.supplier) && Objects.equals(createdAt, that.createdAt) && Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierOrderId, supplier, totalPrice, createdAt, orderStatus);
    }

    @Override
    public String toString() {
        return "SupplierOrders{" +
                "supplierOrderId=" + supplierOrderId +
                ", supplier=" + supplier +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
