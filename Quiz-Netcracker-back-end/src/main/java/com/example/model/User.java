package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    private String role;

    public User(UUID id, String mail, String login, String password, String role) {
        this.id = id;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.login = username;
        this.password = password;
    }

    public User(String username, String mail, String password) {
        this.login = username;
        this.mail = mail;
        this.password = password;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
}