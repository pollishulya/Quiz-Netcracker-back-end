package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            sequenceName = "game_sequence",
            initialValue = 1000
    )
    private Long id;

//    @NotBlank
//    @Size(min = 3, max = 100)
//    private String title;

    @Column(columnDefinition = "name")
    private String name;

    @Column(columnDefinition = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "question_to_game",
            joinColumns = @JoinColumn(columnDefinition = "game_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "question_id"))
    private Set<Question> questionsSet = new HashSet<>();
}