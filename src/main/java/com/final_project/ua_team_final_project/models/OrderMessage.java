package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_messages")
public class OrderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_order_id", nullable = false)
    private SupplierOrder supplierOrder;

    public OrderMessage() {
    }

    public OrderMessage(Long messageId, User user, Order order, SupplierOrder supplierOrder) {
        this.messageId = messageId;
        this.user = user;
        this.order = order;
        this.supplierOrder = supplierOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderMessage that)) return false;
        return Objects.equals(messageId, that.messageId) && Objects.equals(user, that.user) && Objects.equals(order, that.order) && Objects.equals(supplierOrder, that.supplierOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, user, order, supplierOrder);
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "messageId=" + messageId +
                ", user=" + user +
                ", order=" + order +
                ", supplierOrders=" + supplierOrder +
                '}';
    }
}
