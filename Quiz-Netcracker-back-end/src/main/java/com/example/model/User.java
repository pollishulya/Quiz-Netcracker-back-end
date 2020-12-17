package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(generator = "userGenerator")
    @SequenceGenerator(
            name = "userGenerator",
            sequenceName = "userSequence"
    )
    private Long id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    /*@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(columnDefinition = "userId"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "roleId"))
    private Set<Role> roleSet = new HashSet<>();*/

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval=true, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Game> game = new HashSet<>();
}