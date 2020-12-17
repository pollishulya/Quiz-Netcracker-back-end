package com.example.model;

import lombok.Data;

import javax.persistence.*;

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
}
