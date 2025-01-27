package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    private Long dept_id;

    private double total_price;

    private boolean approved_by_head;

    private boolean approved_by_fin_dept;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Long status_id;

    public Order() {
    }

    public Order(Long order_id, Long dept_id, double total_price, boolean approved_by_head, boolean approved_by_fin_dept, Long status_id) {
        this.order_id = order_id;
        this.dept_id = dept_id;
        this.total_price = total_price;
        this.approved_by_head = approved_by_head;
        this.approved_by_fin_dept = approved_by_fin_dept;
        this.status_id = status_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getDept_id() {
        return dept_id;
    }

    public void setDept_id(Long dept_id) {
        this.dept_id = dept_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public boolean isApproved_by_head() {
        return approved_by_head;
    }

    public void setApproved_by_head(boolean approved_by_head) {
        this.approved_by_head = approved_by_head;
    }

    public boolean isApproved_by_fin_dept() {
        return approved_by_fin_dept;
    }

    public void setApproved_by_fin_dept(boolean approved_by_fin_dept) {
        this.approved_by_fin_dept = approved_by_fin_dept;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Double.compare(total_price, order.total_price) == 0 && approved_by_head == order.approved_by_head && approved_by_fin_dept == order.approved_by_fin_dept && Objects.equals(order_id, order.order_id) && Objects.equals(dept_id, order.dept_id) && Objects.equals(createdAt, order.createdAt) && Objects.equals(updatedAt, order.updatedAt) && Objects.equals(status_id, order.status_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, dept_id, total_price, approved_by_head, approved_by_fin_dept, createdAt, updatedAt, status_id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", dept_id=" + dept_id +
                ", total_price=" + total_price +
                ", approved_by_head=" + approved_by_head +
                ", approved_by_fin_dept=" + approved_by_fin_dept +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status_id=" + status_id +
                '}';
    }
}
