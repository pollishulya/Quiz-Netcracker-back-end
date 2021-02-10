package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Player {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column
    private String login;

    @Column
    private String email;

    @Column
    private String photo;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public Player(String name, String email) {
        this.login = name;
        this.email=email;
    }

    public Player(String name) {
        this.login = name;
    }

    public Player(String mail, String login, User user) {
        this.login = login;
        this.email = mail;
        this.user = user;
    }
}
