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
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password_enc", nullable = false)
    private String passwordEnc;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Long userId, Long deptId, String name, Role role, String login, String passwordEnc, String phone, String email) {
        this.userId = userId;
        this.deptId = deptId;
        this.name = name;
        this.role = role;
        this.login = login;
        this.passwordEnc = passwordEnc;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId) && Objects.equals(deptId, user.deptId) && Objects.equals(name, user.name) && Objects.equals(role, user.role) && Objects.equals(login, user.login) && Objects.equals(passwordEnc, user.passwordEnc) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, deptId, name, role, login, passwordEnc, phone, email, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", dept_id=" + deptId +
                ", name='" + name + '\'' +
                ", role_id=" + role +
                ", login='" + login + '\'' +
                ", password_enc='" + passwordEnc + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
