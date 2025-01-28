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
public class SupplierOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierOrderId;

    private Long supplierId;

    private double totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Long statusId;

    public SupplierOrders() {
    }

    public SupplierOrders(Long supplierOrderId, Long supplierId, double totalPrice, Long statusId) {
        this.supplierOrderId = supplierOrderId;
        this.supplierId = supplierId;
        this.totalPrice = totalPrice;
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SupplierOrders that)) return false;
        return Double.compare(totalPrice, that.totalPrice) == 0 && Objects.equals(supplierOrderId, that.supplierOrderId) && Objects.equals(supplierId, that.supplierId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierOrderId, supplierId, totalPrice, createdAt, statusId);
    }

    @Override
    public String toString() {
        return "SupplierOrders{" +
                "supplierOrderId=" + supplierOrderId +
                ", supplierId=" + supplierId +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", statusId=" + statusId +
                '}';
    }
}
