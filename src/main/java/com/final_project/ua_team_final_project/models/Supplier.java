package com.final_project.ua_team_final_project.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name = "suppliers")
public class Supplier {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplier_id;

    private String name;

    private String iban;

    private String address;

    private String contact_phone;

    private String contact_email;

    public Supplier() {
    }

    public Supplier(Long supplier_id, String name, String iban, String address, String contact_phone, String contact_email) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.iban = iban;
        this.address = address;
        this.contact_phone = contact_phone;
        this.contact_email = contact_email;
    }

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Supplier supplier)) return false;
        return Objects.equals(supplier_id, supplier.supplier_id) && Objects.equals(name, supplier.name) && Objects.equals(iban, supplier.iban) && Objects.equals(address, supplier.address) && Objects.equals(contact_phone, supplier.contact_phone) && Objects.equals(contact_email, supplier.contact_email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier_id, name, iban, address, contact_phone, contact_email);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplier_id=" + supplier_id +
                ", name='" + name + '\'' +
                ", iban='" + iban + '\'' +
                ", address='" + address + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", contact_email='" + contact_email + '\'' +
                '}';
    }
}
