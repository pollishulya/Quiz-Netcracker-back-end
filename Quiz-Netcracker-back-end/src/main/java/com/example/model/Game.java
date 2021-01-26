package com.example.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerId")
    private Player player;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Set<Question> questions;

    @Column(name = "photo")
    private String photo;

    public Game(UUID id, String name, String description, Player player, GameRoom gameRoom) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.player = player;
    }
}