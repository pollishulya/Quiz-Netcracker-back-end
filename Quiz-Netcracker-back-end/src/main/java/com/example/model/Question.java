package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

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


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
            //orphanRemoval = true)
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
