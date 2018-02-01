package com.example.service.file;

import com.example.model.User;
import com.example.service.db.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Profile("file")
@Service
@NoArgsConstructor
public class FileCustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public FileCustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByLogin(s);
        if (user != null) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.getLogin(),user.getPassword(), Collections.singletonList(authority));
        } else throw new UsernameNotFoundException("Username not found");
    }
}
