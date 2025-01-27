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
    private Long user_id;

    private Long dept_id;
    private String name;
    private Long role_id;
    private String login;
    private String password_enc;
    private String phone;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Long user_id, Long dept_id, String name, Long role_id, String login, String password_enc, String phone, String email) {
        this.user_id = user_id;
        this.dept_id = dept_id;
        this.name = name;
        this.role_id = role_id;
        this.login = login;
        this.password_enc = password_enc;
        this.phone = phone;
        this.email = email;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword_enc() {
        return password_enc;
    }

    public void setPassword_enc(String password_enc) {
        this.password_enc = password_enc;
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
        return Objects.equals(user_id, user.user_id) && Objects.equals(dept_id, user.dept_id) && Objects.equals(name, user.name) && Objects.equals(role_id, user.role_id) && Objects.equals(login, user.login) && Objects.equals(password_enc, user.password_enc) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, dept_id, name, role_id, login, password_enc, phone, email, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", dept_id=" + dept_id +
                ", name='" + name + '\'' +
                ", role_id=" + role_id +
                ", login='" + login + '\'' +
                ", password_enc='" + password_enc + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
