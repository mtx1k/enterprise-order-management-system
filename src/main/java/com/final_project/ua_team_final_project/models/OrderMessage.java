package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_messages")
public class OrderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private Long userId;

    private Long orderId;

    private Long supplierOrderId;

    public OrderMessage() {
    }

    public OrderMessage(Long messageId, Long userId, Long orderId, Long supplierOrderId) {
        this.messageId = messageId;
        this.userId = userId;
        this.orderId = orderId;
        this.supplierOrderId = supplierOrderId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(Long supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderMessage that)) return false;
        return Objects.equals(messageId, that.messageId) && Objects.equals(userId, that.userId) && Objects.equals(orderId, that.orderId) && Objects.equals(supplierOrderId, that.supplierOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, userId, orderId, supplierOrderId);
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "messageId=" + messageId +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", supplierOrderId=" + supplierOrderId +
                '}';
    }
}
