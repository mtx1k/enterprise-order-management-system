package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "supplier_order_products")
public class SupplierOrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierOrderId;

    private Long orderProductId;

    private Long amount;

    public SupplierOrderProduct() {
    }

    public SupplierOrderProduct(Long id, Long supplierOrderId, Long orderProductId, Long amount) {
        this.id = id;
        this.supplierOrderId = supplierOrderId;
        this.orderProductId = orderProductId;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SupplierOrderProduct that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(supplierOrderId, that.supplierOrderId) && Objects.equals(orderProductId, that.orderProductId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplierOrderId, orderProductId, amount);
    }

    @Override
    public String toString() {
        return "SupplierOrderProduct{" +
                "id=" + id +
                ", supplierOrderId=" + supplierOrderId +
                ", orderProductId=" + orderProductId +
                ", amount=" + amount +
                '}';
    }
}
