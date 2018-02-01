package com.example.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class User {

    public User() {
    }

    public User(String login, String password, String fio) {
        this.login = login;
        this.password = password;
        this.fio = fio;
    }

    @Id
    @NotEmpty(message = "can not be empty")
    @Column(name = "login", nullable = false)
    @Size(min = 3, message = "at least 3 symbols")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    @XmlElement(name = "login")
    private String login;

    @NotEmpty(message = "can not be empty")
    @Column(name = "password", nullable = false)
    @Size(min = 5, message = "at least 5 symbols")
    @XmlElement(name = "password")
    private String password;

    @NotEmpty(message = "can not be empty")
    @Column(name = "fio", nullable = false)
    @Size(min = 5, message = "at least 5 symbols")
    @XmlElement(name = "fio")
    private String fio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User{" +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fio='" + fio + '\'' +
                '}';
    }
}
