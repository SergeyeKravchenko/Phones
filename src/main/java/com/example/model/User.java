package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@ToString(exclude = "phones")
public class User {

    @Id
    @NotEmpty(message = "can not be empty")
    @Column(name = "login", nullable = false)
    @Size(min = 3, message = "at least 3 symbols")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String login;

    @NotEmpty(message = "can not be empty")
    @Column(name = "password", nullable = false)
    @Size(min = 5, message = "at least 5 symbols")
    private String password;

    @NotEmpty(message = "can not be empty")
    @Column(name = "fio", nullable = false)
    @Size(min = 5, message = "at least 5 symbols")
    private String fio;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones;

}
