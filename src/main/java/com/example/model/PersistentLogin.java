package com.example.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persistent_logins")
@Data
public class PersistentLogin {
    @Id
    private String series;

    @Column(name="username", unique=true, nullable=false)
    private String username;

    @Column(name="token", unique=true, nullable=false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_used;
}
