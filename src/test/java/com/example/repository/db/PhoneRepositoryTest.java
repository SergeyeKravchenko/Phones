package com.example.repository.db;

import com.example.model.Phone;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PhoneRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PhoneRepository repository;

    @Test
    public void testfindByUser_Login() {
        User user = new User();
        user.setLogin("first");
        user.setPassword("first");
        user.setFio("first");
        Phone phone = new Phone();
        phone.setFirstname("FirstName");
        phone.setMiddlename("MiddleName");
        phone.setLastname("LastName");
        phone.setPhonemobile("+380501111111");
        phone.setPhonepermanent("3334455");
        phone.setAddress("US");
        phone.setEmail("one@to.com");
        phone.setUser(user);
        entityManager.persist(user);
        entityManager.persist(phone);
        List<Phone> byLogin = repository.findByUser_Login(user.getLogin());
        assertEquals(byLogin.get(0),phone);
    }
}