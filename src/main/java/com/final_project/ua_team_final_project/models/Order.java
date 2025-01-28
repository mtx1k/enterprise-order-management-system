package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table (name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long deptId;

    private double totalPrice;

    private boolean approvedByHead;

    private boolean approvedByFinDept;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Long statusId;

    public Order() {
    }

    public Order(Long orderId, Long deptId, double totalPrice, boolean approvedByHead, boolean approvedByFinDept, Long statusId) {
        this.orderId = orderId;
        this.deptId = deptId;
        this.totalPrice = totalPrice;
        this.approvedByHead = approvedByHead;
        this.approvedByFinDept = approvedByFinDept;
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Double.compare(totalPrice, order.totalPrice) == 0 && approvedByHead == order.approvedByHead && approvedByFinDept == order.approvedByFinDept && Objects.equals(orderId, order.orderId) && Objects.equals(deptId, order.deptId) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt) && Objects.equals(statusId, order.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, deptId, totalPrice, approvedByHead, approvedByFinDept, createdAt, updatedAt, statusId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + orderId +
                ", dept_id=" + deptId +
                ", total_price=" + totalPrice +
                ", approved_by_head=" + approvedByHead +
                ", approved_by_fin_dept=" + approvedByFinDept +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status_id=" + statusId +
                '}';
    }
}
