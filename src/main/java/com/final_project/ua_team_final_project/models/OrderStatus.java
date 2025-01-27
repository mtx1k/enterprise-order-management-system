package com.final_project.ua_team_final_project.models;


import jakarta.persistence.*;

@Entity
@Table(name="order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String statusText;

}
