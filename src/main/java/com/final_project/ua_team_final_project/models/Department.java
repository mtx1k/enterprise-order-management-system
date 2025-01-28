package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name= "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    private String name;

    public Department() {
    }

    public Department(Long deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
        return Objects.equals(deptId, that.deptId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "dept_id=" + deptId +
                ", name='" + name + '\'' +
                '}';
    }
}
