package com.example.model;

import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Player {

    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @NotNull(message = "message.PlayerIdNotValid", groups = {Update.class})
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "message.UsernameNotValid", groups = {Create.class, Update.class})
    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String photo;

    @NotNull(message = "message.UserIdNotValid", groups = {Update.class})
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public Player(UUID id, String name, String email, String photo, User user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.user = user;
    }

    public Player(String name, User user) {
        this.user = user;
        this.name = name;
    }

    public Player(String name, String email) {
        this.name = name;
        this.email=email;
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(String mail, String login, User user) {
        this.name = login;
        this.email = mail;
        this.user = user;
    }
}
