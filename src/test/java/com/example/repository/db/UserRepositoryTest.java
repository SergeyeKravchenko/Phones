package com.example.repository.db;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("Test")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;


    @Test
    public void testFindByLogin() {
        User user = new User("first","first","first");
        entityManager.persist(user);
        User findByLogin = repository.findByLogin(user.getLogin());
        assertEquals(findByLogin,user);
    }

    @Test
    public void testSave() {
        User user = new User("second","second","second");
        repository.save(user);
        User findByLogin = repository.findByLogin(user.getLogin());
        assertEquals(findByLogin,user);
    }
}