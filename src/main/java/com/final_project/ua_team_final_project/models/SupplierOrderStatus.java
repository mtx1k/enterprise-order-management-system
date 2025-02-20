package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "supplier_order_statuses")
public class SupplierOrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierOrderStatusId;

    private String supplierOrderStatusText;

    public SupplierOrderStatus() {}
    public SupplierOrderStatus(Long supplierOrderStatusId, String supplierOrderStatusText) {
        this.supplierOrderStatusId = supplierOrderStatusId;
        this.supplierOrderStatusText = supplierOrderStatusText;
    }
}
