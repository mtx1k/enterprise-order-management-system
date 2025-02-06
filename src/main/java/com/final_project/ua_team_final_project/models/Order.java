package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Department deptId;
    @Column(name = "total_price")
    private double totalPrice;
    private boolean approvedByHead;
    private boolean approvedByFinDept;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatus statusId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    public void addOrderedProduct(OrderedProduct orderedProduct) {
        orderedProducts.add(orderedProduct);
        orderedProduct.setOrder(this);
    }

    public Order() { }

    public Order(Long orderId, Department deptId, double totalPrice, boolean approvedByHead, boolean approvedByFinDept, OrderStatus statusId) {
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
