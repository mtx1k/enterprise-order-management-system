package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Department;
import com.final_project.ua_team_final_project.models.Role;
import com.final_project.ua_team_final_project.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByLogin(String login);
    Optional<User> findByDepartment(Department department);
    Optional<User> findAllByDepartment(Department department);
    Optional<User> findByRole(Role role);
    Optional<User> findAllByRole(Role role);
    Optional<User> findByPhone(String phone);
    Optional<User> findAllByPhone(String phone);
    Optional<User> findAllByEmail(String email);
}
