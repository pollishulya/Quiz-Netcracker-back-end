package com.example.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
@Data
public class Game {
    @Id
    @GeneratedValue(generator = "game_generator")
    @SequenceGenerator(
            name = "game_generator",
            sequenceName = "game_sequence"
    )
    private Long id;

//    @NotBlank
//    @Size(min = 3, max = 100)
//    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "questionGame",
            joinColumns = @JoinColumn(columnDefinition = "gameId"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "questionId"))
    private Set<Question> questionsSet = new HashSet<>();
}