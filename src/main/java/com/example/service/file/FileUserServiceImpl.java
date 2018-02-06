package com.example.service.file;

import com.example.model.User;
import com.example.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Profile("file")
@Service(value = "userService")
public class FileUserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public FileUserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static Map<String, User> usersFileDb = new HashMap<>();

    public static Map<String, User> getUsersFileDb() {
        return usersFileDb;
    }

    public static void setUsersFileDb(Map<String, User> usersFileDb) {
        FileUserServiceImpl.usersFileDb = usersFileDb;
    }

    @Override
    public User findUserByLogin(String login) {
        return usersFileDb.get(login);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersFileDb.put(user.getLogin(), user);
    }
}
