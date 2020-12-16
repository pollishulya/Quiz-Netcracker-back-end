package com.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game")
@Getter
@Setter
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "question_to_game",
            joinColumns = @JoinColumn(columnDefinition = "game_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "question_id"))
    private Set<Question> questionsSet = new HashSet<>();
}