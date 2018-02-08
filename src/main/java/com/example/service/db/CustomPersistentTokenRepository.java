package com.example.service.db;

import com.example.model.PersistentLogin;
import com.example.repository.PersistentLoginRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@NoArgsConstructor
public class CustomPersistentTokenRepository implements PersistentTokenRepository {

    private PersistentLoginRepository loginRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomPersistentTokenRepository.class);

    @Autowired
    public CustomPersistentTokenRepository(PersistentLoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    @Transactional
    public void createNewToken(PersistentRememberMeToken token) {
        LOGGER.info("Create token for :" + token.getUsername());
        PersistentLogin login = new PersistentLogin();
        login.setUsername(token.getUsername());
        login.setSeries(token.getSeries());
        login.setToken(token.getTokenValue());
        login.setLast_used(token.getDate());
        loginRepository.save(login);
    }

    @Override
    @Transactional
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        LOGGER.info("Update token for series :" + seriesId);
        PersistentLogin series = loginRepository.findPersistentLoginBySeries(seriesId);
        series.setToken(tokenValue);
        series.setLast_used(lastUsed);
        loginRepository.save(series);
    }

    @Override
    @Transactional(readOnly = true)
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        LOGGER.info("Trying to find token for :" + seriesId);
        PersistentLogin series = loginRepository.findPersistentLoginBySeries(seriesId);
        return (series != null) ? new PersistentRememberMeToken(series.getUsername(),
                series.getSeries(), series.getToken(),
                series.getLast_used()) : null;
    }

    @Override
    @Transactional
    public void removeUserTokens(String username) {
        LOGGER.info("Trying to find token for :" + username);
        List<PersistentLogin> listLoginByUsername = loginRepository.findPersistentLoginByUsername(username);
        if (listLoginByUsername != null) {
            LOGGER.info("token was found");
            loginRepository.delete(listLoginByUsername);
        }
    }
}
