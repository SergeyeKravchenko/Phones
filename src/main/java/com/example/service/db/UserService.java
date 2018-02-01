package com.example.service.db;

import com.example.model.User;

public interface UserService {
    User findUserByLogin(String login);
    void saveUser(User user);
}
