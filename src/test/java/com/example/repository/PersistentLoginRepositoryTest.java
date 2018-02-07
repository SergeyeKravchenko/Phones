package com.example.repository;

import com.example.model.PersistentLogin;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("Test")
public class PersistentLoginRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersistentLoginRepository repository;

    @Before
    public void populateToken() {
        PersistentLogin login = new PersistentLogin();
        login.setUsername("testName");
        login.setSeries("1");
        login.setLast_used(new Date());
        login.setToken("test");
        entityManager.persist(login);
    }

    @Test
    public void findPersistentLoginBySeries() {
        PersistentLogin series = repository.findPersistentLoginBySeries("1");
        assertEquals(series.getSeries(), "1");
    }

    @Test
    public void findPersistentLoginByUsername() {
        List<PersistentLogin> name = repository.findPersistentLoginByUsername("testName");
        assertEquals(name.get(0).getUsername(), "testName");
    }
}