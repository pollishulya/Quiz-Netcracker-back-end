package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "message.InvalidGameCategoryTitle")
    @Column(name = "name")
    @Getter
    public String title;

    @NotBlank(message = "message.InvalidGameCategoryDescription")
    @Column(name = "description")
    private String description;

    @Builder
    public GameCategory(UUID id, String name, String description) {
        this.id = id;
        this.title = name;
        this.description = description;
    }
}
