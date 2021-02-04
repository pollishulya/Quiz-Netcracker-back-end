package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "gameCategories")
@Data
@NoArgsConstructor
public class GameCategory {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String title;

    @Column(name = "description")
    private String description;

    @Builder
    public GameCategory(UUID id, String name, String description) {
        this.id = id;
        this.title = name;
        this.description = description;
    }
}
