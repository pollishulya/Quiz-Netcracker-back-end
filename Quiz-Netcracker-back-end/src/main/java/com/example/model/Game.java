package com.example.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
public class Game {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Game(UUID id, String name, String description, User user, Set<Question> questionsSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
    }
}