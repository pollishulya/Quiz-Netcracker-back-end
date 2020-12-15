package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;;
import org.hibernate.annotations.Cascade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "mail")
    private String mail;

    @Column(columnDefinition = "login")
    private String login;

    @JsonIgnore
    @Column(columnDefinition = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_to_role",
            joinColumns = @JoinColumn(columnDefinition = "user_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "role_id"))
    private Set<Role> roleSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval=true, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Game> games = new HashSet<>();

}