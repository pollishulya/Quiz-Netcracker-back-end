package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "answers")
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;
    @Column(name="answerIsRight" )
    private boolean right;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private Question question;

    @Builder
    public Answer(UUID id, String title, boolean right, Question question) {
        this.id = id;
        this.title = title;
        this.right = right;
        this.question = question;
    }
}
