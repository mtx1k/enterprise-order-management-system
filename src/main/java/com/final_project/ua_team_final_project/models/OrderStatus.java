package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String statusText;

    public OrderStatus() {
    }

    public OrderStatus(Long statusId, String statusText) {
        this.statusId = statusId;
        this.statusText = statusText;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderStatus that)) return false;
        return Objects.equals(statusId, that.statusId) && Objects.equals(statusText, that.statusText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, statusText);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "statusId=" + statusId +
                ", statusText='" + statusText + '\'' +
                '}';
    }
}
