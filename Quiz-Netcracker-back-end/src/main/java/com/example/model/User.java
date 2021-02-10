package com.example.model;

import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "message.LoginNotValid", groups = {Update.class})
    @Column(name = "login")
    private String login;

    @JsonIgnore
    @NotBlank(message = "message.PasswordTitleNotValid", groups = {Update.class})
    @Column(name = "password")
    private String password;

    @NotBlank(message = "message.RoleNotValid", groups = {Update.class})
    private String role;

    @Column(name = "activation_code")
    private String  activationCode;
    
    @Column(name = "active")
    private Boolean  active;

    public User(UUID id, String mail, String login, String password, String role, Boolean active) {
        this.id = id;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.role = role;
        this.active = active;
    }

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

    public User(String username) {
        this.login = username;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }
}