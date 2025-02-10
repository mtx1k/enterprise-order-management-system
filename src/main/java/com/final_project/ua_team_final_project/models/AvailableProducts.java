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

    @Column(unique = true)
    private String productCode;

    private String name;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    private Double price;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public AvailableProducts() {
    }

    public AvailableProducts(Long productId, String productCode, String name, String description, Category category, Supplier supplier, Double price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AvailableProducts products)) return false;
        return Objects.equals(productId, products.productId) && Objects.equals(productCode, products.productCode) && Objects.equals(name, products.name) && Objects.equals(description, products.description) && Objects.equals(category, products.category) && Objects.equals(supplier, products.supplier) && Objects.equals(price, products.price) && Objects.equals(createdAt, products.createdAt) && Objects.equals(updatedAt, products.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productCode, name, description, category, supplier, price, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "AvailableProducts{" +
                "productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", supplier=" + supplier +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
