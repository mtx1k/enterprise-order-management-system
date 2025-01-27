package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name= "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dept_id;

    private String name;

    public Department() {
    }

    public Department(Long dept_id, String name) {
        this.dept_id = dept_id;
        this.name = name;
    }

    public Long getDept_id() {
        return dept_id;
    }

    public void setDept_id(Long dept_id) {
        this.dept_id = dept_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Department that)) return false;
        return Objects.equals(dept_id, that.dept_id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dept_id, name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "dept_id=" + dept_id +
                ", name='" + name + '\'' +
                '}';
    }
}
