package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.repositories.DepartmentRepository;
import com.final_project.ua_team_final_project.repositories.RoleRepository;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
public class PageDataManager {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    public PageDataManager(UserRepository userRepository, DepartmentRepository departmentRepository,
                           RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    public void setAdminModel(Model model) {
        model.addAttribute("users", userRepository.findAll());
    }

    public void setEditUserModel(Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
    }
}
