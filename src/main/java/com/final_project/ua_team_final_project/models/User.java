package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long deptId;
    private String name;
    private Long roleId;
    private String login;
    private String passwordEnc;
    private String phone;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Long userId, Long deptId, String name, Long roleId, String login, String passwordEnc, String phone, String email) {
        this.userId = userId;
        this.deptId = deptId;
        this.name = name;
        this.roleId = roleId;
        this.login = login;
        this.passwordEnc = passwordEnc;
        this.phone = phone;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordEnc() {
        return passwordEnc;
    }

    public void setPasswordEnc(String passwordEnc) {
        this.passwordEnc = passwordEnc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId) && Objects.equals(deptId, user.deptId) && Objects.equals(name, user.name) && Objects.equals(roleId, user.roleId) && Objects.equals(login, user.login) && Objects.equals(passwordEnc, user.passwordEnc) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, deptId, name, roleId, login, passwordEnc, phone, email, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", dept_id=" + deptId +
                ", name='" + name + '\'' +
                ", role_id=" + roleId +
                ", login='" + login + '\'' +
                ", password_enc='" + passwordEnc + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
