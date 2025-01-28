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
@Table (name = "available_products")
public class AvailableProducts {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productCode;

    private String name;

    private String description;

    private Long categoryId;

    private Long supplierId;

    private Long price;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public AvailableProducts() {
    }

    public AvailableProducts(Long productId, String productCode, String name, String description, Long categoryId, Long supplierId, Long price) {
        this.productId = productId;
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AvailableProducts that)) return false;
        return Objects.equals(productId, that.productId) && Objects.equals(productCode, that.productCode) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(categoryId, that.categoryId) && Objects.equals(supplierId, that.supplierId) && Objects.equals(price, that.price) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productCode, name, description, categoryId, supplierId, price, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "AvailableProducts{" +
                "productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
