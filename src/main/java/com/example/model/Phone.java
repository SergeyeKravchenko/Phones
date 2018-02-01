package com.example.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "phone")
@XmlAccessorType(XmlAccessType.NONE)
public class Phone {

    public Phone() {
    }

    public Phone(String firstname, String lastname, String middlename, String phonemobile, String phonepermanent, String address, String email, User user) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.phonemobile = phonemobile;
        this.phonepermanent = phonepermanent;
        this.address = address;
        this.email = email;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    private Long id;

    @NotEmpty(message = "can not be empty")
    @Column(name="firstname", nullable=false)
    @Size(min = 4, message = "at least 4 symbols")
    @XmlElement(name = "firstname")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String firstname;

    @NotEmpty(message = "can not be empty")
    @Column(name="lastname", nullable=false)
    @Size(min = 4, message = "at least 4 symbols")
    @XmlElement(name = "lastname")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String lastname;

    @NotEmpty(message = "can not be empty")
    @Column(name="middlename", nullable=false)
    @Size(min = 4, message = "at least 4 symbols")
    @XmlElement(name = "middlename")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String middlename;

    @NotEmpty(message = "can not be empty")
    @Pattern(regexp = "^((\\+380)+([0-9]){9})$", message = "not valid number")
    @Column(name="phonemobile", nullable=false)
    @XmlElement(name = "phonemobile")
    private String phonemobile;

    @Column(name="phonepermanent")
    @XmlElement(name = "phonepermanent")
    private String phonepermanent;

    @Column(name="address")
    @XmlElement(name = "address")
    private String address;

    @Email(message = "not valid email format")
    @Column(name="email")
    @XmlElement(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_login", nullable = false, referencedColumnName = "login")
    @XmlElement(name = "user")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getPhonemobile() {
        return phonemobile;
    }

    public void setPhonemobile(String phonemobile) {
        this.phonemobile = phonemobile;
    }

    public String getPhonepermanent() {
        return phonepermanent;
    }

    public void setPhonepermanent(String phonepermanent) {
        this.phonepermanent = phonepermanent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", phonemobile='" + phonemobile + '\'' +
                ", phonepermanent='" + phonepermanent + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                '}';
    }
}
