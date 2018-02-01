package com.example.repository;

import com.example.model.Phone;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("Mysql")
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByUser_Login(String login);
}
