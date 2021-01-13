package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "levels")
@Data
@NoArgsConstructor
public class Level {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "level",fetch = FetchType.LAZY)
    private Set<Question> questions = new HashSet<>();

    @Builder
    public Level(UUID id, String title, String description, Set<Question> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }
}
