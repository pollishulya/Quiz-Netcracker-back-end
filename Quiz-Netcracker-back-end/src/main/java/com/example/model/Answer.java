package com.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private Boolean right;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private Question question;

    @Builder
    public Answer(UUID id, String title, Boolean right, Question question) {
        this.id = id;
        this.title = title;
        this.right = right;
        this.question = question;
    }
}
