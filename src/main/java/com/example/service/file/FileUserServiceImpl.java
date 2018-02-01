package com.example.service.file;

import com.example.model.User;
import com.example.service.db.UserService;
import com.example.dump.Users;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Profile("file")
@Service
public class FileUserServiceImpl implements UserService {

    private static Map<String, User> usersFileDb = new HashMap<>();
    private Users users = new Users();

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public static Map<String, User> getUsersFileDb() {
        return usersFileDb;
    }

    @Override
    public User findUserByLogin(String login) {
        return usersFileDb.get(login);
    }

    @Override
    public void saveUser(User user) {
        usersFileDb.put(user.getLogin(), user);
    }
}
