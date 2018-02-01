package com.example.service.db;

import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "userService")
@Profile("Mysql")
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public User findUserByLogin(String login) {
        User user = repository.findByLogin(login);
        log.info("Found user with login : " + user.getLogin());
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        repository.save(user);
        log.info("Saved user with login : " + user.getLogin());
    }
}
