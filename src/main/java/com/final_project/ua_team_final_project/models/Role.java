package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table (name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Role() {
    }

    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Role role)) return false;
        return Objects.equals(roleId, role.roleId) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
