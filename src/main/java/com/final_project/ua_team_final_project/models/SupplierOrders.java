package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Long getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(Long supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
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
