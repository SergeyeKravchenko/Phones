package com.example.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "can not be empty")
    @Column(name="firstname", nullable=false)
    @Size(min = 4, message = "at least 4 symbols")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String firstname;

    @NotEmpty(message = "can not be empty")
    @Column(name="lastname", nullable=false)
    @Size(min = 4, message = "at least 4 symbols")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String lastname;

    @NotEmpty(message = "can not be empty")
    @Column(name="middlename", nullable=false)
    @Size(min = 4, message = "at least 4 symbols")
    @Pattern(regexp ="^[A-Za-z]+$",message = "Only characters permitted")
    private String middlename;

    @NotEmpty(message = "can not be empty")
    @Pattern(regexp = "^((\\+380)+([0-9]){9})$", message = "not valid number")
    @Column(name="phonemobile", nullable=false)
    private String phonemobile;

    @Column(name="phonepermanent")
    private String phonepermanent;

    @Column(name="address")
    private String address;

    @Email(message = "not valid email format")
    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_login", nullable = false, referencedColumnName = "login")
    private User user;

}
