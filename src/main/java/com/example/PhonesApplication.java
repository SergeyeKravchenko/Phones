package com.example;

import com.example.dump.Phones;
import com.example.dump.Users;
import com.example.service.file.FilePhoneServiceImpl;
import com.example.service.file.FileUserServiceImpl;
import com.example.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication
public class PhonesApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        String prop = System.getProperty("lardi.conf");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PhonesApplication.class);

        if (prop != null && !prop.equals("") && new File(prop).exists()) {
            builder.properties("spring.config.location=" + prop);
        }
        builder.run(args);
    }

    @Bean
    public CommandLineRunner init() {
        return (String... args) -> {
            String property = environment.getRequiredProperty("spring.profiles.active");
            if (property.equals("file")) {
                String path = environment.getRequiredProperty("file.pathtodump");
                Users users = Utils.loadData(Users.class, path);
                Phones phones = Utils.loadData(Phones.class, path);
                if (users != null && phones != null) {
                    FileUserServiceImpl.setUsersFileDb(users.getUsers());
                    FilePhoneServiceImpl.setPhonesFileDb(phones.getPhones());
                }
            }
        };
    }
}
