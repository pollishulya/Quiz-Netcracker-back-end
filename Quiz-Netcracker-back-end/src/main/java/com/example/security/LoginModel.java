package com.example.security;

import com.example.service.validation.annotation.EmailConstraint;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;


public class LoginModel {

    @NotBlank(message = "message.UsernameNotValid", groups = {Create.class, Update.class})
    private String username;

    @NotBlank(message = "message.PasswordTitleNotValid", groups = {Create.class, Update.class})
    private String password;
    private String email;
    private String login;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
}
