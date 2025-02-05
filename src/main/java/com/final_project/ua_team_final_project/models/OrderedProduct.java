package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ordered_products")
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderedProductId;

    @ManyToOne
    @JoinColumn(name = "orders_order_id", nullable = false)
    private Order order;

    private String name;
    private String productCode;

    @Column(nullable = false)
    private Double itemPrice;

    private Long categoryId;
    private Long supplierId;

    @Column(nullable = false)
    private Long amount;

    public OrderedProduct() {
    }

    public OrderedProduct(Long orderedProductId, String productCode, String name, Double itemPrice, Long categoryId, Long supplierId, Long amount) {
        this.orderedProductId = orderedProductId;
        this.productCode = productCode;
        this.name = name;
        this.itemPrice = itemPrice;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderedProduct that)) return false;
        return Double.compare(itemPrice, that.itemPrice) == 0 && Objects.equals(orderedProductId, that.orderedProductId) && Objects.equals(productCode, that.productCode) && Objects.equals(name, that.name) && Objects.equals(categoryId, that.categoryId) && Objects.equals(supplierId, that.supplierId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedProductId, productCode, name, itemPrice, categoryId, supplierId, amount);
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "orderedProductId=" + orderedProductId +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", itemPrice=" + itemPrice +
                ", categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", amount=" + amount +
                '}';
    }
}
