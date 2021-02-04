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

    @Column(name = "views")
    private Long views;

    @Column(name = "rating")
    private Long averageRating;

    //Счётчик оценок игры
    @Column(name = "ratingCount")
    private Long ratingCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameCategoryId")
    private GameCategory gameCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerId")
    private Player player;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Set<Question> questions;

    @Column(name = "photo")
    private String photo;

    @Column(name = "access")
    private String access;

    public Game(UUID id, String name, String description, Player player, GameRoom gameRoom,
                Long views, Long averageRating, Long ratingCount, GameCategory gameCategory) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.views = views;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
        this.gameCategory = gameCategory;
        this.player = player;
        this.access=access;
    }
}