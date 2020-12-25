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
@Table(name = "ans")
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    private boolean right;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<Question> question;

    @Builder
    public Answer(UUID id, String title, boolean right, Set<Question> question) {
        this.id = id;
        this.title = title;
        this.right = right;
        this.question = question;
    }
}
