package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    public Category() {
    }

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }


}
