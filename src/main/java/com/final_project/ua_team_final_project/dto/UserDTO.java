package com.final_project.ua_team_final_project.dto;


import com.final_project.ua_team_final_project.models.Department;

import java.time.LocalDateTime;

public record UserDTO(
        Long userId,
        Department department,
        String name,
        RoleDTO role,
        String login,
        String passwordEnc,
        String phone,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
