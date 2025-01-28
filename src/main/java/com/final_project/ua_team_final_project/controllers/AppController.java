package com.final_project.ua_team_final_project.controllers;

import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public String index(Model model) {

        User userAccount = new User();
        userAccount.setUserId(123L);
        userAccount.setName("Admin");
        userAccount.setPasswordEnc("yoyoyo");

        model.addAttribute("user", userAccount);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
//    @PostMapping("/register")
//    public String postRegister(@ModelAttribute User userAccount) {
//
//        customUserDetailsService.createUserAccount(
//                userAccount.getName(),
//                userAccount.getPasswordEnc()
//        );
//
//        return "redirect:/register";
//    }

}