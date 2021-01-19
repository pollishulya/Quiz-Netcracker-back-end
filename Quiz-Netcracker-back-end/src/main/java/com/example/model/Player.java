package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    private String photo;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    public Player(UUID id, String name, String email, String photo, User user) {
        this.id = id;
        this.name=name;
        this.email = email;
        this.photo = photo;
        this.user = user;
    }

    public Player(String login, User user) {
        this.user = user;
        this.name=login;
    }

    public Player(String login) {
        this.name=login;
    }

    public Player(String mail, String login, User user) {
        this.name=login;
        this.email = mail;
        this.user = user;
    }
}
