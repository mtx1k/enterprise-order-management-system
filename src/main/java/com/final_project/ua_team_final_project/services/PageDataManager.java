package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class PageDataManager {
    private final UserRepository userRepository;

    public PageDataManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void getAdminModel(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
    }
}
