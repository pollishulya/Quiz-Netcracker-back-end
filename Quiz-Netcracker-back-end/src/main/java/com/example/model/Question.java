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
@Table(name = "questions")
@Getter
@Setter
public class Question extends AuditModel {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
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
    @JoinColumn(columnDefinition = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "level_id")
    private Lev level;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "question_to_answer",
            joinColumns = @JoinColumn(columnDefinition = "question_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition  = "answer_id"))
    private Set<Question> questionsSet = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "question_to_game",
            joinColumns = @JoinColumn(columnDefinition = "question_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "game_id"))
    private Set<Game> gamesSet = new HashSet<>();
}
