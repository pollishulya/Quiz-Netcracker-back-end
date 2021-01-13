package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Data
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



    /*@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(columnDefinition = "userId"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "roleId"))
    private Set<Role> roleSet = new HashSet<>();*/

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL},
            //orphanRemoval=true,
            mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Game> game = new HashSet<>();

    @Builder
    public User(UUID id, String mail, String login, String password, String role, Set<Game> game) {
        this.id = id;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.role = role;
        this.game = game;
    }

    public User() {

    }

    public User(String username, String password) {
        this.login = username;
        this.password = password;
    }

    public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
}