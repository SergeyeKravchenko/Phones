package com.example.service.db;

import com.example.model.User;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByLogin(s);
        if (user != null) {
            log.info("Found user with login : " + user.getLogin());
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), AuthorityUtils.NO_AUTHORITIES);
        } else throw new UsernameNotFoundException("Username not found");

    }
}
