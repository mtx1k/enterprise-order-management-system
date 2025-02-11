package com.final_project.ua_team_final_project.dto;

public record SupplierDTO(
        Long supplierId,
        String name,
        String iban,
        String address,
        String contactPhone,
        String contactEmail
) {
}
