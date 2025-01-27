package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.User;
import com.final_project.ua_team_final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword_enc())
                .build();
    }

    public User createUserAccount(String username, String password) {
        User userAccount = new User();

        userAccount.setName(username);
        userAccount.setPassword_enc(passwordEncoder.encode(password));

        return userRepository.save(userAccount);
    }
}