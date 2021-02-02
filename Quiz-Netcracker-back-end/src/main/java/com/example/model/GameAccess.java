package com.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
//@Table(name = "game_access")
@Data
@NoArgsConstructor
public class GameAccess {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    @Column(name = "access")
    private boolean access;

    @Column(name = "activation_code")
    private String  activationCode;



    public GameAccess(UUID id, Player player, Game game, boolean access) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.access = access;
    }

    public GameAccess(Player player, Game game, boolean access) {
        this.player = player;
        this.game = game;
        this.access = access;
    }
}