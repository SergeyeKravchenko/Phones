package com.example.dump;

import com.example.service.db.PhoneService;
import com.example.service.db.UserService;
import com.example.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;

@Component
public class DumpLoaderFromDisc implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Environment environment;

    @Autowired
    UserService userService;

    @Autowired
    PhoneService phoneService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String property = environment.getRequiredProperty("spring.profiles.active");

        if (property.equals("file")) {
            try {
                String path = environment.getRequiredProperty("file.pathtodump");
                Users users = (Users)Utils.loadData(Users.class, path);
                Phones phones = (Phones) Utils.loadData(Phones.class, path);
                userService.setUsers(users);
                phoneService.setPhones(phones);
                } catch (OperationNotSupportedException e) {
//                    NOP
                }
            }
        }
    }