package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "available_products")
public class AvailableProducts {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String product_code;

    private String name;

    private String description;

    private Long category_id;

    private Long supplier_id;

    private Long price;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public AvailableProducts() {
    }

    public AvailableProducts(Long product_id, String product_code, String name, String description, Long category_id, Long supplier_id, Long price) {
        this.product_id = product_id;
        this.product_code = product_code;
        this.name = name;
        this.description = description;
        this.category_id = category_id;
        this.supplier_id = supplier_id;
        this.price = price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AvailableProducts that)) return false;
        return Objects.equals(product_id, that.product_id) && Objects.equals(product_code, that.product_code) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(category_id, that.category_id) && Objects.equals(supplier_id, that.supplier_id) && Objects.equals(price, that.price) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, product_code, name, description, category_id, supplier_id, price, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "AvailableProducts{" +
                "product_id=" + product_id +
                ", product_code='" + product_code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category_id=" + category_id +
                ", supplier_id=" + supplier_id +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
