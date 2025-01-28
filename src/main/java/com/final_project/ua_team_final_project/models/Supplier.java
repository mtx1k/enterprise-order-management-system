package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table (name = "suppliers")
public class Supplier {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String name;

    private String iban;

    private String address;

    private String contactPhone;

    private String contactEmail;

    public Supplier() {
    }

    public Supplier(Long supplierId, String name, String iban, String address, String contactPhone, String contact_email) {
        this.supplierId = supplierId;
        this.name = name;
        this.iban = iban;
        this.address = address;
        this.contactPhone = contactPhone;
        this.contactEmail = contact_email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Supplier supplier)) return false;
        return Objects.equals(supplierId, supplier.supplierId) && Objects.equals(name, supplier.name) && Objects.equals(iban, supplier.iban) && Objects.equals(address, supplier.address) && Objects.equals(contactPhone, supplier.contactPhone) && Objects.equals(contactEmail, supplier.contactEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId, name, iban, address, contactPhone, contactEmail);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplier_id=" + supplierId +
                ", name='" + name + '\'' +
                ", iban='" + iban + '\'' +
                ", address='" + address + '\'' +
                ", contact_phone='" + contactPhone + '\'' +
                ", contact_email='" + contactEmail + '\'' +
                '}';
    }
}
