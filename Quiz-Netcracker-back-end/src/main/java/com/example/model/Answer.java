package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.example.model.Question;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "answers")
public class Answer extends AuditModel {
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(
            name = "answer_generator",
            sequenceName = "answer_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String text;

    @Column(columnDefinition = "right")
    private boolean right;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "question_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Question question;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_to_answer",
            joinColumns = @JoinColumn(columnDefinition= "answer_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "question_id"))
    private Set<Question> questionsSet = new HashSet<>();

}
