package com.example.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "gameCategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCategory {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    @Getter
    public String title;

    @Column(name = "description")
    private String description;
}
