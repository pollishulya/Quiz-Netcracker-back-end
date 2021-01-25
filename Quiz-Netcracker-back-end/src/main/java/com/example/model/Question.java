package com.example.model;

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
@Table(name = "questions")
@Data
@Builder
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelId")
    private Level level;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private Game game;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Set<Answer> answersSet;

    @Builder
    public Question(UUID id, String title, String description, Category category, Level level, Game game, Set<Answer> answersSet) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.game = game;
        this.answersSet = answersSet;
    }
}
