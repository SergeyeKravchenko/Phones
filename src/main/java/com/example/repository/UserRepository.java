package com.example.repository;

import com.example.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("Mysql")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
