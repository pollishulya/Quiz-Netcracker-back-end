package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue(generator = "questionGenerator")
    @SequenceGenerator(
            name = "questionGenerator",
            sequenceName = "questionSequence"
    )
    private Long id;

//    @NotBlank
//    @Size(min = 3, max = 100)
//    private String title;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelId")
    private Level level;


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,
            orphanRemoval = true) //удаление сирот, т.е. при удалении вопроса удаляются и ответы
    @JoinTable(name = "questionAnswer",
            joinColumns = @JoinColumn(name = "questionId"),
            inverseJoinColumns = @JoinColumn(name  = "answerId"))
    private Set<Answer> answersSet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "questionGame",
            joinColumns = @JoinColumn(columnDefinition = "questionId"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "gameId"))
    private Set<Game> gamesSet = new HashSet<>();
}
