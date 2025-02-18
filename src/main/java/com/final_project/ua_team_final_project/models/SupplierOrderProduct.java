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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_order_id", nullable = false)
    private SupplierOrder supplierOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ordered_product_id", nullable = false)
    private OrderedProduct orderProduct;

    private Long amount;

    public SupplierOrderProduct() {
    }

    public SupplierOrderProduct(Long id, SupplierOrder supplierOrder, OrderedProduct orderProduct, Long amount) {
        this.id = id;
        this.supplierOrder = supplierOrder;
        this.orderProduct = orderProduct;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SupplierOrderProduct that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(supplierOrder, that.supplierOrder) && Objects.equals(orderProduct, that.orderProduct) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplierOrder, orderProduct, amount);
    }

    @Override
    public String toString() {
        return "SupplierOrderProduct{" +
                "id=" + id +
                ", supplierOrder=" + supplierOrder +
                ", orderProduct=" + orderProduct +
                ", amount=" + amount +
                '}';
    }
}
