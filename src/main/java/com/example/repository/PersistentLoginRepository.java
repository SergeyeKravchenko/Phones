package com.example.repository;

import com.example.model.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersistentLoginRepository extends JpaRepository<PersistentLogin,String> {
    PersistentLogin findPersistentLoginBySeries(String series);
    List<PersistentLogin> findPersistentLoginByUsername(String userName);
}
