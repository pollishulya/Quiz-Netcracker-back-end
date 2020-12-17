package com.example.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(generator = "answerGenerator")
    @SequenceGenerator(
            name = "answerGenerator",
            sequenceName = "answerSequence"
    )
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "answerIsRight")
    private boolean right;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "question_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Question question;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "questionAnswer",
            joinColumns = @JoinColumn(name= "answerId"),
            inverseJoinColumns = @JoinColumn(name = "questionId"))
    private Set<Question> questionsSet = new HashSet<>();
}
