package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    private String name;

    public Category() {
    }

    public Category(Long category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
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
        return Objects.equals(category_id, category.category_id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                '}';
    }


}
