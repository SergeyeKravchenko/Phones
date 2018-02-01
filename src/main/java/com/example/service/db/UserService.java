package com.example.service.db;

import com.example.model.User;
import com.example.dump.Users;

import javax.naming.OperationNotSupportedException;

public interface UserService {
    public User findUserByLogin(String login);
    public void saveUser(User user);
    public Users getUsers() throws OperationNotSupportedException;
    public void setUsers(Users users) throws OperationNotSupportedException;
}
