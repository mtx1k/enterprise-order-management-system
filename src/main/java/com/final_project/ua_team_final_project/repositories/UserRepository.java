package com.final_project.ua_team_final_project.repositories;

import com.final_project.ua_team_final_project.models.Role;
import com.final_project.ua_team_final_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByLogin(String login);
    Optional<User> findByDeptId(Long deptId);
    Optional<User> findAllByDeptId(Long deptId);
    Optional<User> findByRole(Role role);
    Optional<User> findAllByRole(Role role);
    Optional<User> findByPhone(String phone);
    Optional<User> findAllByPhone(String phone);
    Optional<User> findAllByEmail(String email);

}
