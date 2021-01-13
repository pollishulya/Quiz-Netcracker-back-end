package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL},
            //orphanRemoval=true,
            mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Game> game = new HashSet<>();


    @Builder
    public Player(UUID id, User user, Set<Game> game) {
        this.id = id;
        this.user = user;
        this.game = game;
    }
}

