package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private AvailableProducts product;

    private Integer quantity;
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderItem that)) return false;
        return Objects.equals(orderItemId, that.orderItemId) && Objects.equals(order, that.order) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, order, product, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
